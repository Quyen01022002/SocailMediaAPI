package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.page;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.reponse.PageResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PageMembersMapper {
    PageMembersResponse toResponse(PageMembers page);
    List<PageMembersResponse> toResponseList(List<PageMembers> page);
}
