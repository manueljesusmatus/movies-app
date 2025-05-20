package cl.dev.mmatush.moviesapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "movies")
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = -2226250537211568610L;

    @Id
    private String id;
    private String title;
    private String image;
    private String date;
    private String studio;
    private String duration;
    private List<String> genres;
    private String director;
    private List<String> cast;
    private String description;

}
