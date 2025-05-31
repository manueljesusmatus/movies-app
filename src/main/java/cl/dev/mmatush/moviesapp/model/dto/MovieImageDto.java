package cl.dev.mmatush.moviesapp.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MovieImageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4249605336280072077L;

    private String id;
    private String thumbnail;
    private String fullCover;

}
