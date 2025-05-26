package cl.dev.mmatush.moviesapp.model.document;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Video implements Serializable {

    @Serial
    private static final long serialVersionUID = -5467149293576177671L;

    private String id;
    private String url;
    private String resolution;
    private String size;
    private String duration;

}
