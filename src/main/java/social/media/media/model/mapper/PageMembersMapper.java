package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.SaveItem;
import social.media.media.model.reponse.PageMembersResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PageMembersMapper {
    @Mapping(source = "page.adminId.id", target = "page.adminId")
    PageMembersResponse toResponse(SaveItem page);
    List<PageMembersResponse> toResponseList(List<SaveItem> page);
}
