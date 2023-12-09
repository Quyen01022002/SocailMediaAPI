package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.interations;
import social.media.media.model.entity.notications;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.NoticationsResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface NoticationsMapper {
    NoticationsResponse toResponse(notications notications);
    List<NoticationsResponse> toListResponse(List<notications> notications);

}
