package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.GroupMapper;
import social.media.media.model.mapper.GroupsMembersMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.model.request.GroupsMenberRequest;
import social.media.media.model.request.GroupsRequest;
import social.media.media.service.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupsController {
    @Autowired
    GroupService groupService;

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    GroupsMembersMapper groupsMembersMapper;
    @Autowired
    social.media.media.service.friendsService friendsService;
    @Autowired
    FriendsMapper friendsMapper;


    @PostMapping("/")
    public ApiResponse<GroupsResponse> post(@RequestBody GroupsRequest groups) {
        Groups groups1 = groupMapper.toEntity(groups);
        GroupsResponse savedPage = groupService.addPage(groups1);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedPage);
        return apiResponse;
    }
    @PatchMapping("/{id}")
    public ApiResponse partiallyUpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {


        try {
            String Avatar = (String) updates.get("avatar");
            groupService.updateAvatar(id, Avatar);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }
    @DeleteMapping("/members/")
    public ApiResponse DeleteMembers(@RequestParam("groupID") int groupid,@RequestParam("userID") int userId) {
        try {
            GroupMembers groupMembers=new GroupMembers();
            User user=new User();
            user.setId(userId);
            Groups groups=new Groups();
            groups.setId(groupid);
            groupMembers.setGroup(groups);
            groupMembers.setUser(user);
            groupService.DeleteMembers(groupMembers);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/{id}")
    public ApiResponse<GroupsResponse> getprofile(@PathVariable int id) {
        try {

            GroupsResponse pageDetail = groupService.Detail(id);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(pageDetail);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/isfriend")
    public ApiResponse<List<FriendsResponseDTO>> listFriendGroups(@RequestParam("groupId") int groupId,@RequestParam("userId")int id) {
        try {

            GroupsResponse pageDetail = groupService.Detail(groupId);
            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByUser(user,true);
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();
            for(FriendsResponse item:list)
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();
                if(user.getId()==item.getFriend().getId())
                {
                    friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getFriend());
                }
                else {
                    friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getUser());

                }
                friends isFriend=friendsService.isFriend(user,item.getFriend().getId());
                if(isFriend!=null)
                {
                    friendsResponseDTO.setIsFriends(true);

                }
                else {
                    friendsResponseDTO.setIsFriends(false);

                }
                friendsResponseDTO.setIdFriends(item.getId());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            List<FriendsResponseDTO> list2=new ArrayList<>();
            if (pageDetail != null && pageDetail.getGroupMembers() != null) {
                for (GroupsMembersResponse item : pageDetail.getGroupMembers()) {
                    if (item != null && item.getUser() != null) {
                        list2.add(item.getUser());
                    }
                }
            }

            List<FriendsResponseDTO> commonElements = friendsResponseDTOList.stream()
                    .filter(list2::contains)
                    .collect(Collectors.toList());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(commonElements);
            return apiResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/notfriend")
    public ApiResponse<List<FriendsResponseDTO>> listNotFriendGroups(@RequestParam("groupId") int groupId,@RequestParam("userId")int id) {
        try {

            GroupsResponse pageDetail = groupService.Detail(groupId);
            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByUser(user,true);
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();
            for(FriendsResponse item:list)
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();
                if(user.getId()==item.getFriend().getId())
                {
                    friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getFriend());
                }
                else {
                    friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getUser());

                }
                friends isFriend=friendsService.isFriend(user,item.getFriend().getId());
                if(isFriend!=null)
                {
                    friendsResponseDTO.setIsFriends(true);

                }
                else {
                    friendsResponseDTO.setIsFriends(false);

                }
                friendsResponseDTO.setIdFriends(item.getId());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            List<FriendsResponseDTO> list2=new ArrayList<>();
            if (pageDetail != null && pageDetail.getGroupMembers() != null) {
                for (GroupsMembersResponse item : pageDetail.getGroupMembers()) {
                    if (item != null && item.getUser() != null) {
                        list2.add(item.getUser());
                    }
                }
            }


            List<FriendsResponseDTO> elementsNotInBoth = Stream.concat(friendsResponseDTOList.stream(), list2.stream())
                    .filter(e -> !friendsResponseDTOList.contains(e) || !list2.contains(e))
                    .collect(Collectors.toList());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(elementsNotInBoth);
            return apiResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/department/admin/{id}")
    public ApiResponse<GroupsResponse> GroupByMeAdmin(@PathVariable int id) {
        try {

            List<GroupsResponse> profile = groupService.ListGroupsAdmin(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/admin/{id}")
    public ApiResponse<List<GroupsResponse>> ListGroupAdmin(@PathVariable int id) {
        try {

            List<GroupsResponse> profile = groupService.ListGroupsAdmin(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @GetMapping("/follow/{id}")
    public ApiResponse<List<GroupsResponse>> ListPageFollow(@PathVariable int id) {
        try {

            List<GroupsResponse> profile = groupService.ListGroups(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/follow/post/{id}/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> ListPostFollow(@PathVariable int id, @PathVariable int pagenumber) {
        try {
            List<PostResponseDTO> result=new ArrayList<>();
            List<GroupsResponse> profile = groupService.ListGroups(id);
            for(GroupsResponse item:profile)
            {
                for(PostResponseDTO itemPost:item.getListPost()) {
                    result.add(itemPost);
                }
            }
            Collections.sort(result, Comparator.comparing(PostResponseDTO::getTimeStamp).reversed());
            List<PostResponseDTO> resultResp=new ArrayList<>();
            if (pagenumber*6 > result.size()){

            }
            else if (pagenumber*6 < result.size() && pagenumber*6 < result.size()-6)
            {
                for (int i= pagenumber*6; i< pagenumber*6+6; i++){
                    resultResp.add(result.get(i));
                }
            }
            else if (pagenumber*6 < result.size() && (pagenumber*6+6) >= result.size()){
                for (int i= pagenumber*6; i< result.size(); i++){
                    resultResp.add(result.get(i));
                }
            }
            else {
            resultResp.addAll(result);
            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(resultResp);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/all")
    public ApiResponse<List<GroupsResponse>> ListAll() {
        try {

            List<GroupsResponse> profile = groupService.ListGroupsAll();
//            for(GroupsResponse item:profile)
//            {
//                for(GroupsMembersResponse itemMenber:item.getGroupMembers())
//                {
//
//                }
//
//            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PutMapping("/update/{id}")
    public ApiResponse<PostResponse> Updatepost(@PathVariable int id,@RequestBody GroupsRequest groupsRequest) {
        Groups groupsEnity=groupMapper.toEntity(groupsRequest);
        groupsEnity.setId(id);
        GroupsResponse savedpost = groupService.updateGroups(groupsEnity);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }
    @PutMapping("/updateAdmin/{id}")
    public ApiResponse<PostResponse> UpdateAdminGroup(@PathVariable int id,@RequestBody GroupAdminRequest groupsRequest) {
        GroupsResponse savedpost = groupService.updateAdminGroups(groupsRequest);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }



    @DeleteMapping("/{id}")
    public ApiResponse DeletePage(@PathVariable int id) {
        try {
            groupService.Delete(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/addMembers")
    public ApiResponse addMembers(@RequestBody List<GroupsMenberRequest> user) {
        try {

            List<GroupMembers> groupMembers =groupsMembersMapper.toEntityList(user);
            for(GroupMembers item: groupMembers)
            {

                groupService.addMemberPage(item);

            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }


    @GetMapping("/search")
    public ApiResponse<List<GroupsResponse>> searchGroup(@RequestParam("q") String keyword) {
        try {

            List<GroupsResponse> profile = groupService.searchGroup(keyword);
            for(GroupsResponse item:profile)
            {
                for(GroupsMembersResponse itemMenber:item.getGroupMembers())
                {

                }

            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }


    @GetMapping("/{groupId}/allpost")
    public ApiResponse<List<PostResponseDTO>> getPostsByGroupId(@PathVariable int groupId,
                                        @RequestParam int page, @RequestParam int userid) {
        List<PostResponse> postResponseList = groupService.loadPost(groupId, page, userid);


        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == userid) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                }
                itemPostResponseDTO.setUser_liked(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());

            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }

    @GetMapping("/follow/posts/{userid}/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> ListPostFollow2(@PathVariable int userid, @PathVariable int pagenumber) {
        List<PostResponse> postResponseList = groupService.loadPostOfAllGroupFollow(pagenumber, userid);


        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == userid) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                }
                itemPostResponseDTO.setUser_liked(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }


    @GetMapping("/{groupid}/loadProgress/{iduser}")
    public ApiResponse<List<UserProgress>> loadProgress(@PathVariable int groupid , @PathVariable int iduser){

        List<UserProgress> userProgressList = userService.loadListTeacherProgressInGroup(iduser,groupid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(userProgressList);
        return apiResponse;

    }

    @GetMapping("/{groupid}/{sectorid}/loadProgress/{iduser}")
    public ApiResponse<List<UserProgress>> loadProgressInSector(@PathVariable int groupid, @PathVariable int sectorid, @PathVariable int iduser) {
        List<UserProgress> userProgressList = userService.loadListTeacherProgressInSector(iduser, groupid, sectorid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(userProgressList);
        return apiResponse;

    }
}
