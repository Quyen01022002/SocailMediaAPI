package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PageMembersResponse {
    private int id;
    private FriendsResponseDTO user;
    private PageResponse page;
    private Date createdAt;
    private Date updatedAt;


}
