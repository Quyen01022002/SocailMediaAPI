package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;


@Mapper(componentModel = "spring")
public interface InterationsMapper {
    LikeResponse toResponse(interations like);

}
