package cl.dev.mmatush.moviesapp.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "data.recommendation")
@Component
public class BackofficeProperties {

    private String studio;
    private String actor;
    private String genre;

}
