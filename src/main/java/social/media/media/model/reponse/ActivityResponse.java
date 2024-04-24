package social.media.media.model.reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActivityResponse {

    private List<CommentsResponse> listComment;
    private List<PostResponseDTO> listPostLike;

}
