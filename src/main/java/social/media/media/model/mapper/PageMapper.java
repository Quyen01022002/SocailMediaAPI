package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;
import social.media.media.model.reponse.PageResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PageMapper {
    @Mapping(source = "adminId.id", target = "adminId")
    PageResponse toResponse(page page);
    List<PageResponse> toResponseList(List<page> page);
}
