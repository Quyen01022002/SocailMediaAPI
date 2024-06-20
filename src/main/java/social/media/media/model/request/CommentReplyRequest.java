package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReplyRequest {

    private String cmtReply;
    private int userid;
}
