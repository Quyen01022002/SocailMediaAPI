package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.GroupMembers;

import java.util.List;

@Getter
@Setter
public class GroupAdminRequest {


    private int adminId;

    private int groupId;



}
