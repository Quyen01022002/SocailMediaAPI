package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.*;

import java.sql.Date;
import java.util.List;
@Getter
@Setter
public class PostResponse {
    private int id;

    private String contentPost;

    private Date timeStamp;
    private UserResponsePost CreateBy;

    //Thêm emun status
    private String status;

    private List<postImgResponse> listAnh;
    private List<Comments> lisCmt;
    private List<LikeResponse> listLike;




}
