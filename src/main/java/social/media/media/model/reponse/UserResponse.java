package social.media.media.model.reponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.*;
import social.media.media.model.enums.RoleEnum;

import java.sql.Date;
import java.util.List;
@Getter
@Setter
public class UserResponse {
    private int id;
    private String firstName;

    private String lastName;

    private String phone;

    private Boolean isPhone;

    private String email;
    private String fcm;

    private Boolean isEmail;

    private String profilePicture;
    private String backGroundPicture;
    private RoleEnum role;

    private Boolean isActived;
    private String address;
    private Date createdAt;
    private Date updatedAt;
    private int countFriend;

    private List<PostResponse> postList;
    private List<PostResponse> postListReply;
    private List<FriendsResponseDTO> friendships;




}
