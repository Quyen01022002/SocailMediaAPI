package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.request.CommentRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "commentReply.commentId", target = "cmtReply")
    CommentsResponse toResponse(Comments cmt);
List<CommentsResponse> toListCommentResponse (List<Comments> lis);
    @Mapping(source = "user_id", target = "CreateBy.id")
    @Mapping(source = "post_id", target = "postID.id")
    @Mapping(source = "content_post", target = "commentContent")
    Comments toComment(CommentRequest cmt);
}
