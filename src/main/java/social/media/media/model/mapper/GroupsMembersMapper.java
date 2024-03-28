package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.GroupMembers;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.User;
import social.media.media.model.reponse.GroupsMembersResponse;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.request.GroupsMenberRequest;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupsMembersMapper {
    GroupsMembersResponse toResponse(GroupMembers page);
    List<GroupsMembersResponse> toResponseList(List<GroupMembers> page);
     default List<GroupMembers> toEntityList(List<GroupsMenberRequest> groupsMenberRequests) {
        List<GroupMembers> entityList = new ArrayList<>();

        for (GroupsMenberRequest request : groupsMenberRequests) {
            GroupMembers groupMembers = new GroupMembers();
            User user=new User();
            user.setId(request.getUserId());
            groupMembers.setUser(user);
            Groups groups=new Groups();
            groups.setId(request.getGroupId());
            groupMembers.setGroup(groups);
            // Populate other fields as needed
            entityList.add(groupMembers);
        }

        return entityList;
    }

}
