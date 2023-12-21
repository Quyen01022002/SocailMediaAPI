package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.GroupMembers;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.reponse.GroupsMembersResponse;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.request.GroupsMenberRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupsMembersMapper {
    GroupsMembersResponse toResponse(GroupMembers page);
    List<GroupsMembersResponse> toResponseList(List<GroupMembers> page);
    List<GroupMembers> toEntityList(List<GroupsMenberRequest> groupsMenberRequests);

}
