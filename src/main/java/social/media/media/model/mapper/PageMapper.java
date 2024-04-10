package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Save;
import social.media.media.model.reponse.SaveResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PageMapper {
    @Mapping(source = "adminId.id", target = "adminId")
    SaveResponse toResponse(Save page);
    List<SaveResponse> toResponseList(List<Save> page);
}
