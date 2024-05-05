package social.media.media.model.reponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import social.media.media.model.entity.Message;
import social.media.media.model.entity.MessageMembers;

import java.sql.Date;
import java.util.List;

@Setter
@Getter

public class MessageBoxReponse {

    private int id;
    private String name;
    private String Avatar;
    private List<MessageMembersReponse> messageMembersList;
    private List<MessageReponse> messagesList;


}
