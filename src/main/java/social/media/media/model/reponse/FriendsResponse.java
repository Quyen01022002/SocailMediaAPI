package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.User;

import java.util.Date;
@Setter
@Getter
public class FriendsResponse {

    private int id;

    private UserResponse user;


    private UserResponse friend;

    private Boolean status;
    private Date createdAt;


}
