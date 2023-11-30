package social.media.media.service;

import social.media.media.model.entity.Comments;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.reponse.LikeResponse;

public interface CommentService {
    public CommentsResponse Comments(Comments cmt);
    public CommentsResponse Update(Comments cmt);
    public void Delete(int id);

}
