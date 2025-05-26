package cl.dev.mmatush.moviesapp.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class VideoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4175218290512867865L;

    private String id;
    private String url;
    private String resolution;
    private String size;
    private String duration;

}
