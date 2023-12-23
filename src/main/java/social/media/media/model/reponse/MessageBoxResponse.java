package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MessageBoxResponse {
    private int id;
    private int userId;
    private int friendId;
    private String friendName;
    private String friendAvatar;
    private String newest_mess;
    private Date createdAt;


}
