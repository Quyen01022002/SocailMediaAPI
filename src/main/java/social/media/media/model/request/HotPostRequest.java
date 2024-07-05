package social.media.media.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.enums.StatusCmtPostEnum;
import social.media.media.model.enums.StatusViewPostEnum;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.UserResponsePost;
import social.media.media.model.reponse.postImgResponse;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class HotPostRequest {


    private String contentPost;
    private int groups;
    private int classes;
    private Boolean status;
    private List<postImgResponse> listAnh;
    private StatusViewPostEnum statusViewPostEnum;
    private StatusCmtPostEnum statusCmtPostEnum;
    private int sector;
    private Integer hotinday;

}
