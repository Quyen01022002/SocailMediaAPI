package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.*;
import social.media.media.service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/interations")
@RequiredArgsConstructor
public class InterationsController {
    @Autowired
    InterationService interationService;
    @Autowired
    UserService userLevelService;
    @Autowired
    PostService postService;

    @PostMapping("")
    public ApiResponse post(@RequestParam("post") int postId,@RequestParam("user") int userId ) {
        interations like=new interations();
        Post post=new Post();
        post.setId(postId);
        User user=new User();
        user.setId(userId);
        like.setPostID(post);
        like.setCreateBy(user);
        PostResponse createPost=postService.findOne(postId);
        interationService.Like(like);
       // userLevelService.updatePoint(createPost.getCreateBy().getId(),5);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok();
        return apiResponse;
    }


    @GetMapping("/{userid}/activity")
    public ApiResponse<List<IntactionResponse>> getAllActiviy(@PathVariable int userid) {
        try
        {
            List<IntactionResponse> activityResponse = postService.getAllMyLike(userid);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(activityResponse);
        return apiResponse;
    }
    catch(Exception ex)
    {
        throw new ApplicationException(ex.getMessage());
    }
    }







}
