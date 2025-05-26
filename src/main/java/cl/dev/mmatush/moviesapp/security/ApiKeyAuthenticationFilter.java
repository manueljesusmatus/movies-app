package cl.dev.mmatush.moviesapp.security;

import cl.dev.mmatush.moviesapp.configuration.property.ApiKeyProperties;
import cl.dev.mmatush.moviesapp.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;
    private final ApiKeyProperties apiKeyProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path = request.getRequestURI();

            boolean isWhitelisted = apiKeyProperties.getWhitelist().stream()
                    .anyMatch(path::contains);

            if (isWhitelisted) {
                filterChain.doFilter(request, response);
                return;
            }

            Authentication authentication = authenticationService.getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception exp) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = response.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }
    }
}
