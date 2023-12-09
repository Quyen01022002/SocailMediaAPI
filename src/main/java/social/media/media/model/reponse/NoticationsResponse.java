package social.media.media.model.reponse;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class NoticationsResponse {
    private int id;

    private UserResponsePost user;

    private String contentNotications;

    private Boolean isRead;
    private Timestamp timeStamp;




}
