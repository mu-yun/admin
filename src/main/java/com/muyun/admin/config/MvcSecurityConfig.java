package com.muyun.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.muyun.admin.common.ResponseData;
import com.muyun.admin.common.ResponseStatus;
import com.muyun.admin.entity.Log;
import com.muyun.admin.entity.Menu;
import com.muyun.admin.filter.LogFilter;
import com.muyun.admin.model.UserDetailInfo;
import com.muyun.admin.model.UserLoginInfo;
import com.muyun.admin.service.LogService;
import com.muyun.admin.service.MenuService;
import com.muyun.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    private static final String LOGIN = "Login";
    private static final String LOGOUT = "Logout";

    @Autowired
    private UserService userService;

    private final LogService logService;

    private final MenuService menuService;

    private final ObjectMapper objectMapper;

    @Setter
    private Map<RequestMatcher, String> requestMatcherToNameMap;

    private final Consumer<HttpServletRequest> logConsumer = (request) -> {
        String operation = null;
        for (Map.Entry<RequestMatcher, String> entry : this.requestMatcherToNameMap.entrySet()) {
            RequestMatcher requestMatcher = entry.getKey();
            if (requestMatcher.matches(request)) {
                operation = entry.getValue();
                break;
            }
        }
        log(operation);
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticationConfigurer = http.authorizeRequests();

        List<Menu> menus = menuService.findAll();
        Map<RequestMatcher, String> requestMatcherToNameMap = Maps.newLinkedHashMap();
        menus.forEach(menu -> {
            if (menu.getType() != Menu.MenuType.CATALOG) {
                requestMatcherToNameMap.put(new AntPathRequestMatcher(menu.getUrl(), menu.getHttpMethod().toString()), menu.getName());
                if (Strings.isNotEmpty(menu.getAuthority())) {
                    authenticationConfigurer.antMatchers(menu.getHttpMethod(), menu.getUrl()).hasAuthority(menu.getAuthority());
                }
            }
        });

        this.requestMatcherToNameMap = requestMatcherToNameMap;

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
                //log
                .addLogoutHandler(logLogoutHandler())
                //删除cookies
                .deleteCookies("JSESSIONID")
                .and()
                .addFilterBefore(new LogFilter(logConsumer), FilterSecurityInterceptor.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            log(LOGIN);
            String token = request.getSession().getId();
            UserDetailInfo userDetailInfo = userService.getUserDetailInfo();
            setResponse(response, ResponseData.success(UserLoginInfo.of(token, userDetailInfo)));
        };

    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            setResponse(response, ResponseData.of(ResponseStatus.AUTHENTICATION_FAIL));
        };

    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            setResponse(response, ResponseData.of(ResponseStatus.UNAUTHORIZED));
        };

    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("", accessDeniedException);
            logConsumer.accept(request);
            setResponse(response, ResponseData.of(ResponseStatus.FORBIDDEN));
        };
    }

    private LogoutHandler logLogoutHandler() {
        return (request, response, authentication) -> log(LOGOUT);
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            setResponse(response, ResponseData.success());
        };
    }

    private <T> void setResponse(HttpServletResponse response, ResponseData<T> responseData) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objectMapper.writeValueAsString(responseData));
    }

    private void log(String operation) {
        if (operation == null) {
            return;
        }
        Log log = new Log();
        log.setOperation(operation);
        logService.save(log);
    }
}
