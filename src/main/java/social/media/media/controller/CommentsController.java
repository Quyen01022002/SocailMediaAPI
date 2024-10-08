package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Comments;
import social.media.media.model.reponse.*;
import social.media.media.model.request.CommentReplyRequest;
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

    @GetMapping("/classes/{iduser}/getAllComment/{pagenumber}")
    public ApiResponse<List<CommentsResponse>> getAllCommentInClasses(@PathVariable int iduser,@PathVariable int pagenumber){
        try {

            List<CommentsResponse> profile = commentService.getAllMyCommentClasses(iduser,pagenumber);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }
    }
    @GetMapping("/group/{iduser}/getAllComment/{pagenumber}")
    public ApiResponse<List<CommentsResponse>> getAllCommenByMeInGroup(@PathVariable int iduser,@PathVariable int pagenumber){
        try {

            List<CommentsResponse> profile = commentService.getAllMyCommentByMeInGroup(iduser,pagenumber);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }
    }

    @PutMapping("/setAnswer/{cmtid}")
    public ApiResponse<CommentsResponse> setAnswer (@PathVariable int cmtid){

        CommentsResponse commentsResponse = commentService.setAnswer(cmtid);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(commentsResponse);
        return apiResponse;
    }
    @PutMapping("/setAnswerToCmt/{cmtid}")
    public ApiResponse<CommentsResponse> setAnswerToCmt (@PathVariable int cmtid){

        CommentsResponse commentsResponse = commentService.setAnswerToCmt(cmtid);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(commentsResponse);
        return apiResponse;
    }


    // Comment reply
    @PostMapping("/{cmtid}/reply")
    public ApiResponse<CommentsResponse> reply(@PathVariable int cmtid, @RequestBody CommentReplyRequest commentReplyRequest ){
        CommentsResponse commentsResponse = commentService.replyComment(cmtid, commentReplyRequest);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(commentsResponse);
        return apiResponse;


    }

}
