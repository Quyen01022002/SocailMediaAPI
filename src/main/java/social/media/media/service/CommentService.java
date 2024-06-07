package social.media.media.service;

import social.media.media.model.entity.Comments;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.request.CommentRequest;

import java.util.List;

public interface CommentService {
    public CommentsResponse Comments(Comments cmt);
    public CommentsResponse Comments2(CommentRequest cmt);
    public CommentsResponse Update(Comments cmt);
    public void Delete(int id);

    public List<CommentsResponse> getAllComment(int id);
    public List<CommentsResponse> getAllMyComment(int id);
    public List<CommentsResponse> getAllMyCommentClasses(int id, int pagenumber);
    public CommentsResponse setAnswer(int cmtid);
}

