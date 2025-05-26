package cl.dev.mmatush.moviesapp.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Authentication getAuthentication(HttpServletRequest request);

}
