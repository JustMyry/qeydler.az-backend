package com.exnotis.backend.security.jwt;


import com.exnotis.backend.security.constants.SecurityConstants;
import com.exnotis.backend.security.service.UserDetailsServiceImpl;
import com.exnotis.backend.user.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserServiceImpl userService;
    public JwtFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService, UserServiceImpl userService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService=userDetailsService;
        this.userService=userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/signin") || request.getServletPath().equals("/signup"))
        { filterChain.doFilter(request, response); return; }
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String username;
        final String jwtToken;
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());

        System.out.println("JwtFilter, getsubject of jwt: " + jwtProvider.getSubject(jwtToken));

        username = userService.getUserByUserId(jwtProvider.getSubject(jwtToken)).getUsername();
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtProvider.isTokenValid(username, jwtToken)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }


}
