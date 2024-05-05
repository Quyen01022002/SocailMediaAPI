package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;
import social.media.media.model.reponse.FriendsResponseDTO;

import java.sql.Date;

@Setter
@Getter
public class MessageRequest {


    private String content;

    private int messageBox;
}
