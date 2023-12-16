package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.SavedPost;
import social.media.media.model.entity.User;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.PostRequest;
import social.media.media.service.GroupService;
import social.media.media.service.PostService;
import social.media.media.service.SavedPostService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
    @RequestMapping("/savedpost")
@RequiredArgsConstructor
public class SavedPostsController {
    @Autowired
    SavedPostService savedPostService;
    @Autowired
    PostService postService;
    @Autowired
    GroupService groupService;
    @Autowired
    PostMapper postMapper;

    @PostMapping("/saved/{postId}")
        public ApiResponse<PostResponse> post(@PathVariable int postId) {

        Post postEnity=new Post();
        postEnity.setId(postId);
        SavedPost savedPost=new SavedPost();
        savedPost.setPost(postEnity);
        PostResponse savedpost = savedPostService.savedPost(savedPost);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }
    @PutMapping("/update")
    public ApiResponse<PostResponse> Updatepost(@RequestBody PostRequest post) {
        Post postEnity=postMapper.toEnity(post);
        PostResponse savedpost = postService.updatePost(postEnity,postEnity.getListAnh());

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }
    @GetMapping("/{id}")
    public ApiResponse<List<PostResponseDTO>> ListPostSaved(@PathVariable int id) {
        try {
            List<PostResponseDTO> postResponseList =savedPostService.updatePost(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(postResponseList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

}

