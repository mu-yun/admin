package com.muyun.springboot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muyun.springboot.common.ResponseData;
import com.muyun.springboot.common.ResponseStatus;
import com.muyun.springboot.entity.Menu;
import com.muyun.springboot.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author muyun
 * @date 2020/4/26
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class MvcSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MenuRepository menuRepository;

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticationConfigurer = http.authorizeRequests();

        List<Menu> menus = menuRepository.findAll();
        menus.forEach(menu -> {
            if (menu.getType() != Menu.MenuType.CATALOG && Strings.isNotEmpty(menu.getAuthority())) {
                authenticationConfigurer.antMatchers(menu.getHttpMethod(), menu.getUrl()).hasAuthority(menu.getAuthority());
            }
        });

        authenticationConfigurer
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .permitAll()
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                //仅允许通过POST请求退出登陆
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutSuccessHandler(logoutSuccessHandler())
                //删除cookies
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String token = request.getSession().getId();
            setResponse(response, ResponseData.success(token));
        };

    }

    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            setResponse(response, ResponseData.of(ResponseStatus.AUTHENTICATION_FAIL));
        };

    }

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            setResponse(response, ResponseData.of(ResponseStatus.UNAUTHORIZED));
        };

    }

    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("", accessDeniedException);
            setResponse(response, ResponseData.of(ResponseStatus.FORBIDDEN));
        };
    }


    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> setResponse(response, ResponseData.success());
    }

    private <T> void setResponse(HttpServletResponse response, ResponseData<T> responseData) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objectMapper.writeValueAsString(responseData));
    }
}
