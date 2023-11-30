package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.page;
import social.media.media.model.reponse.GroupsResponse;
import social.media.media.model.reponse.PageResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupsResponse toResponse(Groups Groups);
    List<GroupsResponse> toResponseList(List<Groups> Groups);
}
