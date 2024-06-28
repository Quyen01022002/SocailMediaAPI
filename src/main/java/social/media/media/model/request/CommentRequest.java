package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CommentRequest {
    private int user_id;
    private int post_id;
    private String content_post;
    private Date timeStamp;
    private Boolean isAnwser;
}
