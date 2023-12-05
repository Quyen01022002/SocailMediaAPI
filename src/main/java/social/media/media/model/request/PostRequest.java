package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.UserResponsePost;
import social.media.media.model.reponse.postImgResponse;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PostRequest {


    private String contentPost;

    private List<postImgResponse> listAnh;




}
