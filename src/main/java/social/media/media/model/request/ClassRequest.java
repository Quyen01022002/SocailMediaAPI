package social.media.media.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Groups;

@Getter
@Setter
public class ClassRequest {


    private String name;

    private int groups;
    private String description;
    private String Avatar;
    private String BackAvatar;



}
