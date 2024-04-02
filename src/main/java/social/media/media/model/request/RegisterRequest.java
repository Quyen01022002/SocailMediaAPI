package social.media.media.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social.media.media.model.enums.RoleEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String phone;
    private RoleEnum roleEnum;
    private String password;
}
