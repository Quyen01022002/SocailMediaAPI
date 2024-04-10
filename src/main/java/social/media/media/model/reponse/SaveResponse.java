package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.SaveItem;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class SaveResponse {

    private int adminId;
    private Date createdAt;
    private Date updatedAt;

    private List<SaveItem> groupMembers;



}
