package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.*;
import social.media.media.model.reponse.GroupsMembersResponse;
import social.media.media.model.request.ClassMemberRequest;
import social.media.media.model.request.GroupsMenberRequest;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ClassMembersMapper {
//    GroupsMembersResponse toResponse(GroupMembers page);
//    List<GroupsMembersResponse> toResponseList(List<GroupMembers> page);
     default List<ClassMembers> toEntityList(List<ClassMemberRequest> groupsMenberRequests) {
        List<ClassMembers> entityList = new ArrayList<>();

        for (ClassMemberRequest request : groupsMenberRequests) {
            ClassMembers groupMembers = new ClassMembers();
            User user=new User();
            user.setId(request.getUser());
            groupMembers.setUser(user);
            Classes groups=new Classes();
            groups.setId(request.getClasses());
            groupMembers.setClasses(groups);
            entityList.add(groupMembers);
        }

        return entityList;
    }

}
