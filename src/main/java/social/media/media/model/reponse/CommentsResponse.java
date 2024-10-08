package social.media.media.model.reponse;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class CommentsResponse {
    private int commentId;


    private PostResponse postID;

    private Date timeStamp;

    private String commentContent;



    private UserResponse CreateBy;

    private boolean isAnwser;

    private List<CommentsResponse> commentsReplyList;

    private int cmtReply;

}
