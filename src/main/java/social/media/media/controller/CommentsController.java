package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Comments;
import social.media.media.model.reponse.*;
import social.media.media.model.request.CommentRequest;
import social.media.media.service.CommentService;
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
    @PostMapping("/cmt2")
    public ApiResponse<CommentsResponse> post2(@RequestBody CommentRequest cmt) {

        CommentsResponse savedCmt = commentService.Comments2(cmt);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedCmt);
        return apiResponse;
    }
    @PutMapping("/update")
    public ApiResponse<CommentsResponse> update(@RequestBody Comments cmt) {

        CommentsResponse savedCmt = commentService.Update(cmt);

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

    @GetMapping("/{id}/getcmnt")
    public ApiResponse<List<CommentsResponse>> getAllComment(@PathVariable int id){
        try {

            List<CommentsResponse> profile = commentService.getAllComment(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }
    }
    @GetMapping("/{iduser}/getAllComment")
    public ApiResponse<List<CommentsResponse>> getAllMyComment(@PathVariable int iduser){
        try {

            List<CommentsResponse> profile = commentService.getAllMyComment(iduser);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }
    }

}
