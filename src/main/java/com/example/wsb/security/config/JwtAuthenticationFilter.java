package com.example.wsb.security.config;


import com.example.wsb.user.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (request.getServletPath().contains("/api/v1")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(jwt + " jwt!");
        username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwt == null || !jwtService.isTokenValid(jwt, userDetails)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
            return;
        }
        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;
        }
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
            return;
        }
    }

}