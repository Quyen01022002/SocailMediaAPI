package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.reponse.LikeResponse;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentsResponse toResponse(Comments cmt);

}
