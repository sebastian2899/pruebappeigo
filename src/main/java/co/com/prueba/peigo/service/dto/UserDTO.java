package co.com.prueba.peigo.service.dto;

import lombok.Data;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Data
public class UserDTO {

    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private String tipoDocumento;

    private String numeroDocumento;

    private String email;

    private String telefono;

    private String direccion;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

   
}
