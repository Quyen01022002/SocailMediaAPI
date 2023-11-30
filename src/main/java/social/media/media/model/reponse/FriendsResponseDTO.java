package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class FriendsResponseDTO {

    private int id;
    private String firstName;

    private String lastName;

    private String phone;

    private Boolean isPhone;

    private String email;

    private Boolean isEmail;

    private String profilePicture;


    private Boolean isActived;
    private String address;
    private Date createdAt;
    private Date updatedAt;
}
