package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Groups;
import social.media.media.model.reponse.GroupsResponse;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.model.request.GroupsRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupMapper {
    @Mapping(source = "adminId.id", target = "adminId")
    GroupsResponse toResponse(Groups Groups);
    Groups toEntity(GroupsRequest Groups);

    @Mapping(source = "adminId", target = "adminId.id")
    Groups groupAmdinRequestToGroupEntity(GroupAdminRequest groupAdminRequest);
    List<GroupsResponse> toResponseList(List<Groups> Groups);
}
