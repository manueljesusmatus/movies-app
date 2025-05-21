package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.configuration.property.ApiKeyProperties;
import cl.dev.mmatush.moviesapp.security.ApiKeyAuthentication;
import cl.dev.mmatush.moviesapp.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String INVALID_API_KEY = "Invalid Api Key";

    private final ApiKeyProperties apiKeyProperties;

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(apiKeyProperties.getApiKey());
        if (apiKey == null || !apiKey.equals(apiKeyProperties.getApiSecret())) {
            log.error(INVALID_API_KEY);
            throw new BadCredentialsException(INVALID_API_KEY);
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

}
