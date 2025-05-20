package cl.dev.mmatush.moviesapp.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Getter
public class XPathProperties {

    @Value("${data.page.xpath.id}")
    private String id;

    @Value("${data.page.xpath.title}")
    private String title;

    @Value("${data.page.xpath.image}")
    private String image;

    @Value("${data.page.xpath.date}")
    private String date;

    @Value("${data.page.xpath.studio}")
    private String studio;

    @Value("${data.page.xpath.duration}")
    private String duration;

    @Value("${data.page.xpath.genres}")
    private String genres;

    @Value("${data.page.xpath.director}")
    private String director;

    @Value("${data.page.xpath.cast}")
    private String cast;

    @Value("${data.page.xpath.description}")
    private String description;

    @Value("${data.page.url}")
    private String url;

    private Map<String, String> xpaths;

    @PostConstruct
    public void init() {
         xpaths = Map.of(
                "id", id,
                "title", title,
                "image", image,
                "date", date,
                "studio", studio,
                "duration", duration,
                "genres", genres,
                "director", director,
                "cast", cast,
                "description", description
        );
    }

}
