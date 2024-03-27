package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.enums.GenderEnum;

import java.sql.Date;

@Getter
@Setter
public class UserRequest2 {

    private String firstName;

    private String lastName;

    private String email;
    private String phone;
    private String profilePicture;
    private String backGroundPicture;


}
