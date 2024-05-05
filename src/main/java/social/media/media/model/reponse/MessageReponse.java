package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.User;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
public class MessageReponse {

    private int id;

    private UserResponsePost user;

    private String content;

    private Timestamp createdAt;
}
