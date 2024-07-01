package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.enums.StatusCmtPostEnum;
import social.media.media.model.enums.StatusViewPostEnum;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.HotPostRequest;
import social.media.media.model.request.PostRequest;
import social.media.media.service.CommentService;
import social.media.media.service.GroupService;
import social.media.media.service.PostService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostsController {
    @Autowired
    friendsService friendsService;
    @Autowired
    PostService postService;
    @Autowired
    GroupService groupService;
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentService commentService;

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
                            break;
                        } else {
                            itemPostResponseDTO.setUser_liked(false);
                        }
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());
                    itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                    itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
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
        postEnity.setStatusCmtPostEnum(post.getStatusCmtPostEnum());
        postEnity.setStatusViewPostEnum(post.getStatusViewPostEnum());
        PostResponse savedpost = postService.addPost(postEnity, postEnity.getListAnh());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }
    @PostMapping("/post/important")
    public ApiResponse<PostResponse> postImportant(@RequestBody HotPostRequest post) {
        Post postEnity = postMapper.HotToEnity(post);
        postEnity.setStatusCmtPostEnum(StatusCmtPostEnum.NOTUSER);
        postEnity.setStatusViewPostEnum(StatusViewPostEnum.ALLUSER);
        PostResponse savedpost = postService.addPostImportant(postEnity, postEnity.getListAnh());

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

    @GetMapping("/get/{iduser}/{id}")
    public ApiResponse<PostResponse> getpost(@PathVariable int id, @PathVariable int iduser) {

        PostResponse itempost = postService.findOne(id);
        PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
        itemPostResponseDTO.setId(itempost.getId());
        itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
        itemPostResponseDTO.setLike_count(itempost.getListLike().size());
        itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
        itemPostResponseDTO.setContentPost(itempost.getContentPost());
        itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
        itemPostResponseDTO.setGroupid(itempost.getGroupid());
        for (LikeResponse itemlike : itempost.getListLike()) {
            if (itemlike.getCreateBy().getId() == iduser) {
                itemPostResponseDTO.setUser_liked(true);
                break;
            }
            itemPostResponseDTO.setUser_liked(false);
        }
        itemPostResponseDTO.setListAnh(itempost.getListAnh());
        itemPostResponseDTO.setStatus(itempost.getStatus());
        itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
        itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(itemPostResponseDTO);
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

    @GetMapping("/teacher/{id}/top10")
    public ApiResponse<List<PostResponseDTO>> getTop10onTeacher(@PathVariable int id) {
        List<PostResponse> postResponseList = postService.getTop10Teacher(id);
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

    @GetMapping("/classes/{userid}/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> getPostOfClassHaveUser(@PathVariable int userid, @PathVariable int pagenumber) {
        List<PostResponse> postResponseList = postService.getPostClass(userid,pagenumber);
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

    @GetMapping("/classes/top5/{userid}")
    public ApiResponse<List<PostResponseDTO>> getTop5OnMonth(@PathVariable int userid) {
        List<PostResponse> postResponseList = postService.getTop5OnMonth(userid);
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

    @GetMapping("/{userid}/search")
    public ApiResponse<List<PostResponseDTO>> getSearchResultPost(@PathVariable int userid, @RequestParam("q") String keyword, @RequestParam("pagenumber") int pagenumber) {
        List<PostResponse> postResponseList = postService.searchPost(keyword, pagenumber);
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

//    @MessageMapping("/hottop10")
//    @SendTo("/topic/top10")
//    public ApiResponse<List<PostResponseDTO>> getTop10WS(@PathVariable int id) {
//        // Lấy danh sách top 10 từ cơ sở dữ liệu
//        List<PostResponse> top10Posts = postService.getTop10();
//        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
//        for (PostResponse itempost : top10Posts) {
//            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
//            itemPostResponseDTO.setId(itempost.getId());
//            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
//            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
//            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
//            itemPostResponseDTO.setContentPost(itempost.getContentPost());
//            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
//            itemPostResponseDTO.setGroupid(itempost.getGroupid());
//            for (LikeResponse itemlike : itempost.getListLike()) {
//                if (itemlike.getCreateBy().getId() == id) {
//                    itemPostResponseDTO.setUser_liked(true);
//                    break;
//                }
//                itemPostResponseDTO.setUser_liked(false);
//            }
//            itemPostResponseDTO.setListAnh(itempost.getListAnh());
//            itemPostResponseDTO.setStatus(itempost.getStatus());
//
//            postResponseDTOList.add(itemPostResponseDTO);
//        }
//
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.ok(postResponseDTOList);
//        return apiResponse;
//    }


    @GetMapping("/classes/countYear/{userid}")
    public ApiResponse<String> getCountOnYear(@PathVariable int userid) {

        String rs = postService.getPostCountStringByMonthAndAdminId(userid);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;
    }



    @GetMapping("/allgroup/{iduser}")
    public ApiResponse<List<PostResponseDTO>> getPostAllGroupFollow(@PathVariable int iduser, @RequestParam("pagenumber") int pagenumber){

        List<PostResponseDTO> postResponseDTOList = postService.getPostOfAllGroupFollow(iduser,pagenumber);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }


    @GetMapping("/getPostReply/{idteacher}/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> getPostReply(@PathVariable int idteacher, @PathVariable int pagenumber){

        List<PostResponseDTO> postResponseDTOList = postService.getPostReply(idteacher,pagenumber);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;

    }


    @PostMapping("/{postid}/notSectorMe")
    public ApiResponse notSectorMe (@PathVariable int postid){

        PostResponse postResponse = postService.notSectorMe(postid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok();
        return apiResponse;
    }

    @GetMapping("/teacher/{teacherid}/notReply/")
    public ApiResponse<List<PostResponseDTO>> loadPostNotReply(@PathVariable int teacherid){
        List<PostResponseDTO> postResponseDTOList = postService.getPostNotReply(teacherid);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }

    @GetMapping("/postReporteds/group/{groupid}/{pagenumber}")
    public ApiResponse<List<PostResponseDTO>> loadPostReported(@PathVariable int groupid, @PathVariable int pagenumber){
        List<PostResponseDTO> postResponseDTOList = postService.loadPostReportedInGroup(pagenumber, groupid);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }


    @PutMapping("/{postid}/blockCmt")
    public ApiResponse<PostResponseDTO> blockCmt(@PathVariable int postid){
        PostResponseDTO responseDTO = postService.blockCmt(postid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(responseDTO);
        return apiResponse;
    }
    @PutMapping("/{postid}/unblockCmt")
    public ApiResponse<PostResponseDTO> unblockCmt(@PathVariable int postid){
        PostResponseDTO responseDTO = postService.unblockCmt(postid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(responseDTO);
        return apiResponse;
    }
}

