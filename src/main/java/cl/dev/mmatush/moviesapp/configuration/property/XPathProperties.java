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
        private String fullCover;
        private String thumbnail;
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
        xpaths = Map.ofEntries(
                Map.entry("id", xpath.getId()),
                Map.entry("title", xpath.getTitle()),
                Map.entry("fullCover", xpath.getFullCover()),
                Map.entry("thumbnail", xpath.getThumbnail()),
                Map.entry("date", xpath.getDate()),
                Map.entry("studio", xpath.getStudio()),
                Map.entry("duration", xpath.getDuration()),
                Map.entry("genres", xpath.getGenres()),
                Map.entry("director", xpath.getDirector()),
                Map.entry("cast", xpath.getCast()),
                Map.entry("description", xpath.getDescription())
        );
    }
}
