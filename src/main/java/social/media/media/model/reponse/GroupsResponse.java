package social.media.media.model.reponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.GroupMembers;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class GroupsResponse {
    private int id;

    private String name;

    private String description;
    private String Avatar;
    private Date createdAt;
    private Date updatedAt;
    private int adminId;

    private List<GroupsMembersResponse> groupMembers;
    private List<PostResponseDTO> listPost;

}
