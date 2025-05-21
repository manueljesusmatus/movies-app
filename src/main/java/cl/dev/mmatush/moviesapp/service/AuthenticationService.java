package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.configuration.property.ApiKeyProperties;
import cl.dev.mmatush.moviesapp.security.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ApiKeyProperties apiKeyProperties;

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = "Baeldung";

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(apiKeyProperties.getApiKey());
        if (apiKey == null || !apiKey.equals(apiKeyProperties.getApiSecret())) {
            throw new BadCredentialsException("Invalid API Key");
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

}
