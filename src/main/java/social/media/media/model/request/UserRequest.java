package social.media.media.model.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.enums.GenderEnum;
import social.media.media.model.reponse.postImgResponse;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String firstName;

    private String lastName;

    private String address;
    private Date DoB;
    private GenderEnum gender;

}
