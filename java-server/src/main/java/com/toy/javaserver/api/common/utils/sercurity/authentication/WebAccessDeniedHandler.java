package com.toy.javaserver.api.common.utils.sercurity.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());

        if (accessDeniedException instanceof AccessDeniedException) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // 접근권한 체크
            User getPrincipal = (User) authentication.getPrincipal();

            boolean userAuthenticationChecked = getPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"));

            if (authentication != null && userAuthenticationChecked) {
                request.setAttribute("message", "접근권한이 없는 사용자입니다.");
            } else {
                request.setAttribute("message", "로그인 권한이 없는 아이디입니다.");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // 로그아웃 처리
                SecurityContextHolder.clearContext();
            }

        } else {
            logger.info(accessDeniedException.getClass().getCanonicalName());
        }
        // request.getRequestDispatcher("/error/denied").forward(request, response);
    }

}
