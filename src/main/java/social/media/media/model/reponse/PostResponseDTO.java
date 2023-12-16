package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Comments;

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
    private String status;
    private int comment_count;
    private int savedId;

    private int like_count;
    private Boolean user_liked;
    private List<postImgResponse> listAnh;


}
