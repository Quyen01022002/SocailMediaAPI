package social.media.media.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import social.media.media.model.entity.GroupMembers;
import social.media.media.model.entity.Groups;
import social.media.media.model.reponse.GroupsResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.request.GroupAdminRequest;

import java.util.List;

public interface GroupService {
    public GroupsResponse addPage(Groups groups);
    public GroupsResponse updateGroups(Groups groups);
    public GroupsResponse updateAdminGroups(GroupAdminRequest groups);
    public GroupsResponse Detail(int id);
    public List<GroupsResponse> ListGroups(int id);

    Page<GroupsResponse> listGroups(int id, Pageable pageable);

    public List<GroupsResponse> ListGroupsAll();
    public List<GroupsResponse> searchGroup(String keyword);
    public List<GroupsResponse> ListGroupsAdmin(int id);
    public GroupsResponse updatePage(Groups groups);
    public void addMemberPage(GroupMembers groupsMembersResponse);
    public void Delete(int id);
    public void DeleteMembers(GroupMembers page);
    public void updateAvatar(int id,String Avatar);
    public List<GroupsResponse> Search(String keyword);
    public List<PostResponse> loadPost(int groupid, int page, int userid);
    public List<PostResponse> loadPostOfAllGroupFollow(int pagenumber, int userid);
    public List<PostResponse> loadHotPostOfAllGroupFollow(int pagenumber, int userid);

}
