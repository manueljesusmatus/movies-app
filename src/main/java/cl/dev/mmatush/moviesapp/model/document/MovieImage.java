package cl.dev.mmatush.moviesapp.model.document;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MovieImage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5852475183938149368L;

    private String id;
    private String thumbnail;
    private String fullCover;

}
