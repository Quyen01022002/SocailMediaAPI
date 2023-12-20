package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.page;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.reponse.PageResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PageMembersMapper {
    @Mapping(source = "page.adminId.id", target = "page.adminId")
    PageMembersResponse toResponse(PageMembers page);
    List<PageMembersResponse> toResponseList(List<PageMembers> page);
}
