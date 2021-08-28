package ru.zotov.carracing.security.filter;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Created by ZotovES on 19.08.2021
 */
public class UserIdFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest servletReq = ((HttpServletRequest) servletRequest);
        HttpServletResponse servletRes = ((HttpServletResponse) servletResponse);
        String userId = servletReq.getHeader("X-USER-ID");
        if (userId != null) {
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));
            var auth = new UsernamePasswordAuthenticationToken(new CustomUser(userId, "user", "pass", authorities), "pass",
                    authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
