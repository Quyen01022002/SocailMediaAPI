package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.IntactionResponse;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface InterationsMapper {
    LikeResponse toResponse(interations like);

    IntactionResponse toIResponse(interations interations);

    List<IntactionResponse> toLResponse(List<interations> interationsList);

}
