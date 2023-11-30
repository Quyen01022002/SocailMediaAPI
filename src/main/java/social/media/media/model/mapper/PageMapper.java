package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;
import social.media.media.model.reponse.PageResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PageMapper {
    PageResponse toResponse(page page);
    List<PageResponse> toResponseList(List<page> page);
}
