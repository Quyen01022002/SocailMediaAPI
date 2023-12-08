package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;

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
    private String address;
    private int countFriend;

    private List<PostResponseDTO> postList;
    private List<FriendsResponseDTO> friendships;




}
