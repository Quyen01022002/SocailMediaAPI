package social.media.media.service;


import social.media.media.model.entity.ClassMembers;
import social.media.media.model.entity.Classes;

import social.media.media.model.reponse.ClassResponse;


import java.util.List;

public interface ClassService {
    public ClassResponse addClasses(Classes groups);
    public ClassResponse updateClasses(Classes groups);
//    public GroupsResponse updateAdminGroups(GroupAdminRequest groups);
    public ClassResponse Detail(int id);
//    public List<GroupsResponse> ListGroups(int id);
//
//    Page<GroupsResponse> listGroups(int id, Pageable pageable);
//
//    public List<GroupsResponse> ListGroupsAll();
//    public List<GroupsResponse> searchGroup(String keyword);
    public List<ClassResponse> ListGroupsAdmin(int id);

//    public GroupsResponse updatePage(Groups groups);
    public void addMemberClass(ClassMembers groupsMembersResponse);
//    public void Delete(int id);
    public void DeleteMembers(int page);
    public void updateAvatar(int id,String Avatar);

}
