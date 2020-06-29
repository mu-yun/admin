package com.muyun.springboot.filter;

import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author muyun
 * @date 2020/6/25
 */
@AllArgsConstructor
public class LogFilter extends OncePerRequestFilter {

    private final Consumer<HttpServletRequest> logConsumer;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
        logConsumer.accept(request);
    }

}
