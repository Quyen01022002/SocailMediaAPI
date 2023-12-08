package social.media.media.model.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.GroupMembers;
import social.media.media.model.reponse.postImgResponse;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class GroupsRequest {


    private String name;

    private String description;
   // private List<GroupsMenberRequest> groupMembers;



}
