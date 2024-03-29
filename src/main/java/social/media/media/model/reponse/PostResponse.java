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
import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
public class PostResponse {
    private int id;

    private String contentPost;

    private Timestamp timeStamp;
    private UserResponsePost CreateBy;

    //Thêm emun status
    private Boolean status;

    private List<postImgResponse> listAnh;
    private List<Comments> lisCmt;
    private List<LikeResponse> listLike;




}
