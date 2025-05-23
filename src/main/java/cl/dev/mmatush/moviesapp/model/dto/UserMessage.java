package cl.dev.mmatush.moviesapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -4610927546060914691L;

    private String user;
    private String message;

}
