package cl.dev.mmatush.moviesapp.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class MovieDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3132827237965511013L;

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
