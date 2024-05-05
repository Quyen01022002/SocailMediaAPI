package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.User;

import java.sql.Date;



@Getter
@Setter

public class MessageMembersReponse {

    private int id;
    private UserResponsePost user;



}
