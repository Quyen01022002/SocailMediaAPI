package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;

import java.sql.Date;

@Getter
@Setter
public class ReportReponse {

    private int id;

    private UserResponsePost reporterID;


    private PostResponseDTO reportedPostID;


    private String reason;

    private Date timestamp;
    private Date status;




}
