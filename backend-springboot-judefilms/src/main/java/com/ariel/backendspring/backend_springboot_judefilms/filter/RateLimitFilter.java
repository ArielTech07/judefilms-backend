package com.ariel.backendspring.backend_springboot_judefilms.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket(boolean login) {
        int requests = login ? 5 : 100;

        Bandwidth limit = Bandwidth.builder()
                .capacity(requests)
                .refillGreedy(requests, Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Bucket resolveBucket(String key, boolean login) {
        return buckets.computeIfAbsent(
                key,
                ignored -> createBucket(login));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Dejar pasar las peticiones preflight OPTIONS del navegador sin contarlas en
        // el rate limit
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getServletPath();

        boolean login = "POST".equalsIgnoreCase(request.getMethod())
                && "/api/users/login".equals(path);

        String ip = request.getRemoteAddr();
        String key = ip + (login ? ":login" : ":api");

        Bucket bucket = resolveBucket(key, login);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Retry-After", "60");

            response.getWriter().write(
                    "{\"message\":\"Demasiadas solicitudes. Intenta más tarde.\"}");
        }
    }
}