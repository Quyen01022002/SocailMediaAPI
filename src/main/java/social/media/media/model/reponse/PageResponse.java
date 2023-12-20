package social.media.media.model.reponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PageResponse {
    private int id;

    private String name;

    private String description;
    private Date createdAt;
    private Date updatedAt;
    private int adminId;

   // private List<PageMembersResponse> groupMembers;





}
