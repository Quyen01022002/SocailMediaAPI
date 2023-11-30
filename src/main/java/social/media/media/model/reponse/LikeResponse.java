package social.media.media.model.reponse;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.interations;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class LikeResponse {
    private int interactionId;

    private Date timeStamp;

    private Boolean isLiked;

    private UserResponse CreateBy;




}
