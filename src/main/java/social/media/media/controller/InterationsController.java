package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.*;
import social.media.media.service.InterationService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/interations")
@RequiredArgsConstructor
public class InterationsController {
    @Autowired
    InterationService interationService;

    @PostMapping("")
    public ApiResponse post(@RequestParam("post") int postId,@RequestParam("user") int userId ) {
        interations like=new interations();
        Post post=new Post();
        post.setId(postId);
        User user=new User();
        user.setId(userId);
        like.setPostID(post);
        like.setCreateBy(user);
        interationService.Like(like);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok();
        return apiResponse;
    }

}
