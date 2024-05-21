package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.enums.RoleEnum;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String profilePicture;
    private String backGroundPicture;
    private String address;
    private int countFriend;
    private int idFriends;
    private  Boolean isFriends;
    private List<PostResponseDTO> postList;
    private List<FriendsResponseDTO> friendships;
    private RoleEnum role;



}
