package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Post;

import java.sql.Date;

@Getter
@Setter
public class IntactionResponse {

    private int interactionId;
    private PostResponse postID;
    private Date timeStamp;

    private UserResponse CreateBy;
    private Boolean isLiked;
}
