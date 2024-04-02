package social.media.media.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social.media.media.model.enums.RoleEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private RoleEnum roleEnum;
    private String Avatar;
    private int id;
}
