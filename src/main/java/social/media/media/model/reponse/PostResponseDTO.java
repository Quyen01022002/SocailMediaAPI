package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Comments;
import social.media.media.model.enums.StatusCmtPostEnum;
import social.media.media.model.enums.StatusViewPostEnum;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PostResponseDTO {
    private int id;

    private String contentPost;

    private Timestamp timeStamp;
    private UserResponsePost CreateBy;

    //Thêm emun status
    private Boolean status;
    private int comment_count;
    private int savedId;
    private int groupid;

    private int like_count;
    private Boolean user_liked;
    private List<postImgResponse> listAnh;
    private StatusViewPostEnum statusViewPostEnum;
    private StatusCmtPostEnum statusCmtPostEnum;

}
