package com.stackroute.stockapp.filter;

/*
 * create a class AppFilter
 * use @Component annotation for this class to make it a bean
 * extends OncePerRequestFilter
 * override doFilterInternal method
 * get the token from the header
 * validate the token using Jwts.parser except for the endpoints /api/v1/user/register and /api/v1/user/login
 * if the token is valid then set the user in the request
 * else throw an exception
 *  
 */

 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@Component
public class AppFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("authorization");
        if (request.getRequestURI().equals("/api/v1/user/register") || request.getRequestURI().equals("/api/v1/user/login")) {
            filterChain.doFilter(request, response);
        } else {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    String email = Jwts.parser().setSigningKey("CTS-BATCH3-SUCCESS").parseClaimsJws(token).getBody().getSubject();
                    request.setAttribute("email", email);
                    filterChain.doFilter(request, response);
                } catch (SignatureException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        }
    }
}
