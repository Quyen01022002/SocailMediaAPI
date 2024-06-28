package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProgress {
    private UserResponseDTO userResponse;
    private int countAllPostReply;
    private int countPostReplied;
}
