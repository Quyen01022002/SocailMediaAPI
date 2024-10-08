package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.ResetPasswordRequest;
import social.media.media.model.request.UserRequest;
import social.media.media.model.request.UserRequest2;
import social.media.media.service.AuthenticationService;
import social.media.media.service.PostService;
import social.media.media.service.UserService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FriendsMapper friendsMapper;
    @Autowired
    friendsService friendsService;
    @Autowired
    AuthenticationService service;

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getprofile(@PathVariable int id) {
        try {

            UserResponse profile = userService.profile(id);
            List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
            for(PostResponse itempost:profile.getPostList())
            {
                PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                itemPostResponseDTO.setId(itempost.getId());
                itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                itemPostResponseDTO.setSave_count(itempost.getSave_count());
                itemPostResponseDTO.setContentPost(itempost.getContentPost());
                itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                for(LikeResponse itemlike:  itempost.getListLike())
                {
                    if(itemlike.getCreateBy().getId()==id)
                    {
                        itemPostResponseDTO.setUser_liked(true);

                    }
                    else {
                        itemPostResponseDTO.setUser_liked(false);
                    }
                }
                if (itempost.getSaveItemList().size() == 0)
                    itemPostResponseDTO.setUser_saved(false);
                else
                for (SaveItem itemlike : itempost.getSaveItemList()) {
                    if (itemlike.getPage().getId() == id) {
                        itemPostResponseDTO.setUser_saved(true);
                        break;
                    }
                    itemPostResponseDTO.setUser_saved(false);
                }
                itemPostResponseDTO.setListAnh(itempost.getListAnh());
                itemPostResponseDTO.setStatus(itempost.getStatus());
                itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                itemPostResponseDTO.setGroupname(itempost.getGroupname());
                postResponseDTOList.add(itemPostResponseDTO);
            }
            UserResponseDTO responseDTO=new UserResponseDTO();
            responseDTO.setProfilePicture(profile.getProfilePicture());
            responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
            responseDTO.setId(profile.getId());
            responseDTO.setEmail(profile.getEmail());
            responseDTO.setAddress(profile.getAddress());
            responseDTO.setFirstName(profile.getFirstName());
            responseDTO.setFriendships(profile.getFriendships());
            responseDTO.setCountFriend(profile.getCountFriend());
            responseDTO.setPostList(postResponseDTOList);
            responseDTO.setPhone(profile.getPhone());
            responseDTO.setLastName(profile.getLastName());
            responseDTO.setRole(profile.getRole());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(responseDTO);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/{id}/checkoldPassword")
    public ApiResponse<UserResponseDTO> getuserbyEmailAndPw(@PathVariable int id, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {

                UserResponse profile = userService.profileByEmailAndPw(resetPasswordRequest);
            List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
            for(PostResponse itempost:profile.getPostList())
            {
                PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                itemPostResponseDTO.setId(itempost.getId());
                itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                itemPostResponseDTO.setSave_count(itempost.getSave_count());
                itemPostResponseDTO.setContentPost(itempost.getContentPost());
                itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                for(LikeResponse itemlike:  itempost.getListLike())
                {
                    if(itemlike.getCreateBy().getId()==profile.getId())
                    {
                        itemPostResponseDTO.setUser_liked(true);

                    }
                    else {
                        itemPostResponseDTO.setUser_liked(false);
                    }
                }
                if (itempost.getSaveItemList().size() == 0)
                    itemPostResponseDTO.setUser_saved(false);
                else
                for (SaveItem itemlike : itempost.getSaveItemList()) {
                    if (itemlike.getPage().getId() == profile.getId()) {
                        itemPostResponseDTO.setUser_saved(true);
                        break;
                    }
                    itemPostResponseDTO.setUser_saved(false);
                }
                itemPostResponseDTO.setListAnh(itempost.getListAnh());
                itemPostResponseDTO.setStatus(itempost.getStatus());
                itemPostResponseDTO.setGroupname(itempost.getGroupname());
                itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                postResponseDTOList.add(itemPostResponseDTO);
            }
            UserResponseDTO responseDTO=new UserResponseDTO();
            responseDTO.setProfilePicture(profile.getProfilePicture());
            responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
            responseDTO.setId(profile.getId());
            responseDTO.setEmail(profile.getEmail());
            responseDTO.setAddress(profile.getAddress());
            responseDTO.setFirstName(profile.getFirstName());
            responseDTO.setFriendships(profile.getFriendships());
            responseDTO.setCountFriend(profile.getCountFriend());
            responseDTO.setPostList(postResponseDTOList);
            responseDTO.setPhone(profile.getPhone());
            responseDTO.setLastName(profile.getLastName());
            responseDTO.setRole(profile.getRole());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(responseDTO);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @GetMapping("/checkemailrole/{email}")
    public ApiResponse<UserResponseDTO> getuserbyEmail(@PathVariable String email) {
        try {

            UserResponse profile = userService.profileByEmail(email);
            List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
            for(PostResponse itempost:profile.getPostList())
            {
                PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                itemPostResponseDTO.setId(itempost.getId());
                itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                itemPostResponseDTO.setSave_count(itempost.getSave_count());
                itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                itemPostResponseDTO.setContentPost(itempost.getContentPost());
                itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                for(LikeResponse itemlike:  itempost.getListLike())
                {
                    if(itemlike.getCreateBy().getId()==profile.getId())
                    {
                        itemPostResponseDTO.setUser_liked(true);

                    }
                    else {
                        itemPostResponseDTO.setUser_liked(false);
                    }
                }
                if (itempost.getSaveItemList().size() == 0)
                    itemPostResponseDTO.setUser_saved(false);
                else
                for (SaveItem itemlike : itempost.getSaveItemList()) {
                    if (itemlike.getPage().getId() == profile.getId()) {
                        itemPostResponseDTO.setUser_saved(true);
                        break;
                    }
                    itemPostResponseDTO.setUser_saved(false);
                }

                itemPostResponseDTO.setListAnh(itempost.getListAnh());
                itemPostResponseDTO.setStatus(itempost.getStatus());
itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                itemPostResponseDTO.setGroupname(itempost.getGroupname());
                postResponseDTOList.add(itemPostResponseDTO);
            }
            UserResponseDTO responseDTO=new UserResponseDTO();
            responseDTO.setProfilePicture(profile.getProfilePicture());
            responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
            responseDTO.setId(profile.getId());
            responseDTO.setEmail(profile.getEmail());
            responseDTO.setAddress(profile.getAddress());
            responseDTO.setFirstName(profile.getFirstName());
            responseDTO.setFriendships(profile.getFriendships());
            responseDTO.setCountFriend(profile.getCountFriend());
            responseDTO.setPostList(postResponseDTOList);
            responseDTO.setPhone(profile.getPhone());
            responseDTO.setLastName(profile.getLastName());
            responseDTO.setRole(profile.getRole());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(responseDTO);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/")
    public ApiResponse<UserResponseDTO> getprofileOther(@RequestParam("userId") int id,@RequestParam("friendId") int friendId) {
        try {

            UserResponse profile = userService.profile(friendId);
            List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
            for(PostResponse itempost:profile.getPostList())
            {
                PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                itemPostResponseDTO.setId(itempost.getId());
                itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                itemPostResponseDTO.setSave_count(itempost.getSave_count());
                itemPostResponseDTO.setContentPost(itempost.getContentPost());
                itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                for(LikeResponse itemlike:  itempost.getListLike())
                {
                    if(itemlike.getCreateBy().getId()==id)
                    {
                        itemPostResponseDTO.setUser_liked(true);

                    }
                    else {
                        itemPostResponseDTO.setUser_liked(false);
                    }
                }
                if (itempost.getSaveItemList().size() == 0)
                    itemPostResponseDTO.setUser_saved(false);
                else
                for (SaveItem itemlike : itempost.getSaveItemList()) {
                    if (itemlike.getPage().getId() == id) {
                        itemPostResponseDTO.setUser_saved(true);
                        break;
                    }
                    itemPostResponseDTO.setUser_saved(false);
                }
                itemPostResponseDTO.setListAnh(itempost.getListAnh());
                itemPostResponseDTO.setStatus(itempost.getStatus());
itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
itemPostResponseDTO.setGroupname(itempost.getGroupname());
                postResponseDTOList.add(itemPostResponseDTO);
            }
            UserResponseDTO responseDTO=new UserResponseDTO();
            responseDTO.setProfilePicture(profile.getProfilePicture());
            responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
            responseDTO.setId(profile.getId());
            responseDTO.setEmail(profile.getEmail());
            responseDTO.setAddress(profile.getAddress());
            responseDTO.setFirstName(profile.getFirstName());
            responseDTO.setFriendships(profile.getFriendships());
            responseDTO.setCountFriend(profile.getCountFriend());
            responseDTO.setPostList(postResponseDTOList);
            responseDTO.setPhone(profile.getPhone());
            responseDTO.setLastName(profile.getLastName());
            responseDTO.setRole(profile.getRole());
            User user=new User();
            user.setId(id);
            friends isFriend=friendsService.isFriend(user,friendId);

            if(isFriend!=null)
            {
                responseDTO.setIsFriends(true);
                responseDTO.setIdFriends(isFriend.getId());
            }
            else {
                responseDTO.setIsFriends(false);

            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(responseDTO);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/list20follow")
    public ApiResponse<List<FriendsResponseDTO>> getlist20() {
        try {
            List<UserResponse> list = userService.List20Follow();

            List<FriendsResponseDTO> result = friendsMapper.toListFriendsResponseDto(list);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(result);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/search/{id}")
    public ApiResponse<List<FriendsResponseDTO>> search(@RequestParam("key") String key,@PathVariable int id) {
        try {
            List<UserResponse> list = userService.Search(key);
            User user=new User();
            user.setId(id);
            List<FriendsResponseDTO> result = friendsMapper.toListFriendsResponseDto(list);
            List<FriendsResponseDTO> resultFinal = new ArrayList<>();
            for(FriendsResponseDTO item: result)
            {

                friends isFriend=friendsService.isFriend(user,item.getId());
                if(isFriend!=null)
                {
                    item.setIsFriends(true);

                }
                else {
                    item.setIsFriends(false);

                }
                resultFinal.add(item);

            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(resultFinal);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @DeleteMapping("/unfollow")
    public ApiResponse DeleteMembers(@RequestBody userFollow friends) {
        try {
            userService.unFollow(friends);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PostMapping("/follow")
    public ApiResponse Follow(@RequestBody User user) {
        try {

            userService.Follow(user);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PatchMapping("/{id}")
    public ApiResponse partiallyUpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {


        try {
            String Avatar = (String) updates.get("avatar");
            userService.updateAvatar(id, Avatar);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }
    @PatchMapping("/background/{id}")
    public ApiResponse backgroundUpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {


        try {
            String Avatar = (String) updates.get("background");
            userService.updateBackGround(id, Avatar);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }
    @PatchMapping("/fcm/{id}")
    public ApiResponse UpdateUserFCM(@PathVariable int id, @RequestBody Map<String, Object> updates) {


        try {
            String fcm = (String) updates.get("fcm");
            userService.fcm(id, fcm);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }

    @PutMapping("/{id}")
    public ApiResponse UpdateUser(@PathVariable int id, @RequestBody UserRequest updatedUser) {


        try {

            userService.update(id, userMapper.toEntity(updatedUser));

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }


    @PutMapping("/up/{id}")
    public ApiResponse UpdateUser2(@PathVariable int id, @RequestBody UserRequest2 updatedUser) {


        try {

            userService.update2(id, userMapper.toEntity2(updatedUser));

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }
    @PutMapping("/{id}/changePw")
    public ResponseEntity<ApiResponse> UpdatePw(@PathVariable int id, @RequestBody ResetPasswordRequest request) {
        try {
            return ResponseEntity.ok(service.resetPassword(request));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }

    @GetMapping("/{iduser}/loadProgress")
    public ApiResponse<UserProgress> loadProgressInSector( @PathVariable int iduser) {
        UserProgress userProgress = userService.loadListTeacherProgress(iduser);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(userProgress);
        return apiResponse;

    }


    @GetMapping("/{userid}/posts/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> getPostMe(@PathVariable int userid, @PathVariable int pagenumber) {
        List<PostResponse> postResponseList = postService.getMyPost(userid,pagenumber);
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setSave_count(itempost.getSave_count());
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
            if (itempost.getSaveItemList().size() == 0)
                itemPostResponseDTO.setUser_saved(false);
            else
            for (SaveItem itemlike : itempost.getSaveItemList()) {
                if (itemlike.getPage().getId() == userid) {
                    itemPostResponseDTO.setUser_saved(true);
                    break;
                }
                itemPostResponseDTO.setUser_saved(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            itemPostResponseDTO.setGroupname(itempost.getGroupname());
            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }

    @GetMapping("/other/{userid}/posts/{myid}/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> getPostOther(@PathVariable int userid, @PathVariable int pagenumber, @PathVariable int myid) {
        List<PostResponse> postResponseList = postService.getOtherPost(userid,pagenumber);
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setSave_count(itempost.getSave_count());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == myid) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                }
                itemPostResponseDTO.setUser_liked(false);
            }
            if (itempost.getSaveItemList().size() == 0)
                itemPostResponseDTO.setUser_saved(false);
            else
            for (SaveItem itemlike : itempost.getSaveItemList()) {
                if (itemlike.getPage().getId() == myid) {
                    itemPostResponseDTO.setUser_saved(true);
                    break;
                }
                itemPostResponseDTO.setUser_saved(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            itemPostResponseDTO.setGroupname(itempost.getGroupname());
            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }
}
