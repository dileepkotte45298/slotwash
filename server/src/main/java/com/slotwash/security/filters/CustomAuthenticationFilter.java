package com.slotwash.security.filters;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.slotwash.security.JwtTokenGenerator;
import com.slotwash.security.provider.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired JwtTokenGenerator jwtTokenGenerator;
    @Autowired CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Bearer");
        
        if(bearerToken==null){
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = bearerToken.substring(7).strip();
        String username = jwtTokenGenerator.getUsernameFromToken(jwtToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(userDetails!=null){
            if(jwtTokenGenerator.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
                filterChain.doFilter(request, response);
            }
            else{
                throw new BadCredentialsException("Invalid JWT Token");
            }
        }
    }
}
