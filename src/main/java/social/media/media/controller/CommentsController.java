package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.reponse.PageResponse;
import social.media.media.service.CommentService;
import social.media.media.service.PageService;
import social.media.media.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;


    @PostMapping("/")
    public ApiResponse<CommentsResponse> post(@RequestBody Comments cmt) {

        CommentsResponse savedCmt = commentService.Comments(cmt);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedCmt);
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse DeleteCmt(@PathVariable int id) {
        try {
            commentService.Delete(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }



}
