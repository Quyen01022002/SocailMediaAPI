package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.ClassMembers;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ClassResponse {
    private int id;
    private UserResponsePost teacher;
    private String name;

    private int groups;
    private String description;
    private String Avatar;
    private String BackAvatar;
    private Date createdAt;
    private Date updatedAt;


    private List<ClassMembersResponse> classMembers;
    private List<PostResponseDTO> listPost;
}
