package cl.dev.mmatush.moviesapp.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth")
@Component
public class ApiKeyProperties {

    private String apiKey;
    private String apiSecret;
    private List<String> whitelist;

}


