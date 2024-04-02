package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.PostRequest;
import social.media.media.service.GroupService;
import social.media.media.service.PostService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostsController {
    @Autowired
    friendsService friendsService;
    @Autowired
    PostService postService;
    @Autowired
    GroupService groupService;
    @Autowired
    PostMapper postMapper;

    @GetMapping("/{id}")
    public ApiResponse<List<PostResponseDTO>> getfriendlist(@PathVariable int id) {

        try {

            User user = new User();
            user.setId(id);
            List<GroupsResponse> profile = groupService.ListGroups(id);
            List<FriendsResponse> list = friendsService.findByUser(user, true);
            List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
            for (FriendsResponse item : list) {
                for (PostResponse itempost : item.getUser().getPostList()) {
                    PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
                    itemPostResponseDTO.setId(itempost.getId());
                    itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                    itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                    itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                    itemPostResponseDTO.setContentPost(itempost.getContentPost());
                    itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                    itemPostResponseDTO.setGroupid(itempost.getGroupid());
                    for (LikeResponse itemlike : itempost.getListLike()) {
                        if (itemlike.getCreateBy().getId() == id) {
                            itemPostResponseDTO.setUser_liked(true);

                        } else {
                            itemPostResponseDTO.setUser_liked(false);
                        }
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());

                    postResponseDTOList.add(itemPostResponseDTO);
                }
                for (PostResponse itempost : item.getFriend().getPostList()) {
                    PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
                    itemPostResponseDTO.setId(itempost.getId());
                    itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                    itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                    itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                    itemPostResponseDTO.setContentPost(itempost.getContentPost());
                    itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                    itemPostResponseDTO.setGroupid(itempost.getGroupid());
                    for (LikeResponse itemlike : itempost.getListLike()) {
                        if (itemlike.getCreateBy().getId() == id) {
                            itemPostResponseDTO.setUser_liked(true);

                        }
                        itemPostResponseDTO.setUser_liked(false);
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());

                    postResponseDTOList.add(itemPostResponseDTO);
                }


            }
            for (GroupsResponse item : profile) {
                for (PostResponseDTO itemPost : item.getListPost()) {
                    postResponseDTOList.add(itemPost);

                }
            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(postResponseDTOList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PostMapping("/post")
    public ApiResponse<PostResponse> post(@RequestBody PostRequest post) {
        Post postEnity = postMapper.toEnity(post);
        PostResponse savedpost = postService.addPost(postEnity, postEnity.getListAnh());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }

    @PutMapping("/update")
    public ApiResponse<PostResponse> Updatepost(@RequestBody PostRequest post) {
        Post postEnity = postMapper.toEnity(post);
        PostResponse savedpost = postService.updatePost(postEnity, postEnity.getListAnh());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse DeletePage(@PathVariable int id) {
        try {
            postService.Delete(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }
    }

    @GetMapping("/{id}/top10")
    public ApiResponse<List<PostResponseDTO>> getTop10(@PathVariable int id) {
        List<PostResponse> postResponseList = postService.getTop10();
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
                if (itemlike.getCreateBy().getId() == id) {
                    itemPostResponseDTO.setUser_liked(true);

                }
                itemPostResponseDTO.setUser_liked(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());

            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }


    @GetMapping("/{userid}/search")
    public ApiResponse<List<PostResponseDTO>> getSearchResultPost(@PathVariable int userid, @RequestParam("q") String keyword) {
        List<PostResponse> postResponseList = postService.searchPost(keyword);
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

                }
                itemPostResponseDTO.setUser_liked(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());

            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }
}

