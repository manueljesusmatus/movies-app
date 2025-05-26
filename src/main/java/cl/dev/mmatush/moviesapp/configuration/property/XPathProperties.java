package cl.dev.mmatush.moviesapp.configuration.property;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "data.page")
@Component
public class XPathProperties {

    private String url;
    private XPath xpath;
    private Map<String, String> xpaths;

    @Getter
    @Setter
    public static class XPath {
        private String id;
        private String title;
        private String image;
        private String date;
        private String studio;
        private String duration;
        private String genres;
        private String director;
        private String cast;
        private String description;
    }

    @PostConstruct
    public void init() {
        xpaths = Map.of(
                "id", xpath.getId(),
                "title", xpath.getTitle(),
                "image", xpath.getImage(),
                "date", xpath.getDate(),
                "studio", xpath.getStudio(),
                "duration", xpath.getDuration(),
                "genres", xpath.getGenres(),
                "director", xpath.getDirector(),
                "cast", xpath.getCast(),
                "description", xpath.getDescription()
        );
    }
}
