package social.media.media.model.reponse;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.User;

import java.sql.Date;

@Getter
@Setter
public class GroupsMembersResponse {
    private int id;
    private FriendsResponseDTO user;


}
