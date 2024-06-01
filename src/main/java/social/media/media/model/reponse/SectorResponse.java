package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Groups;

@Getter
@Setter
public class SectorResponse {

    private int id;
    private String name;
    private String description;
    private String avatar;
    private int groupId;
    private String nameGroup;
    private String avatarGroup;


}
