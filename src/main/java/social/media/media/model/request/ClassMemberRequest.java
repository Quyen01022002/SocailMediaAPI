package social.media.media.model.request;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.User;

@Getter
@Setter
public class ClassMemberRequest {

    private int user;


    private int classes;



}
