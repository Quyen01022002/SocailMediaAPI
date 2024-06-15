package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Report;
import social.media.media.model.entity.SavedPost;
import social.media.media.model.entity.User;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.model.reponse.ReportReponse;
import social.media.media.model.request.PostRequest;
import social.media.media.model.request.ReportRequest;
import social.media.media.service.GroupService;
import social.media.media.service.PostService;
import social.media.media.service.ReportService;
import social.media.media.service.SavedPostService;

import java.util.List;

@RestController
    @RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    PostService postService;
    @Autowired
    GroupService groupService;
    @Autowired
    PostMapper postMapper;

    @GetMapping("/{id}")
    public ApiResponse<List<ReportReponse>> ListPostSaved(@PathVariable int id) {
        try {
            List<ReportReponse> postResponseList =reportService.listReportPost(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(postResponseList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @DeleteMapping("/{id}")
    public ApiResponse NoAppcept(@PathVariable int id) {
        try {
            reportService.Delete(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/waiting/{id}")
    public ApiResponse<List<PostResponseDTO>> ListPostWating(@PathVariable int id) {
        try {
            List<PostResponseDTO> postResponseList =reportService.listPosstWaiting(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(postResponseList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PatchMapping("/duyet/{id}")
    public ApiResponse Duyet(@PathVariable int id) {
        try {
            reportService.DuyetPost(id);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
@PostMapping("/post/{postid}")
    public ApiResponse reportPost(@PathVariable int postid) {
        try {
            Post post=new Post();
            post.setId(postid);
            Report report=new Report();
            report.setReportedPostID(post);
            reportService.reportPost(report);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PostMapping("/posts/{postid}")
    public ApiResponse reportPost2(@PathVariable int postid, @RequestBody ReportRequest reportRequest) {
        try {

            if (reportService.searchByIdUserReporterAndPost(reportRequest.getCreateById(),postid)== null)
            {
            Post post=new Post();
            post.setId(postid);
            User userReporter = new User();
            userReporter.setId(reportRequest.getCreateById());
            Report report=new Report();
            report.setReportedPostID(post);
            report.setReporterID(userReporter);
            report.setReason(reportRequest.getContent_report());
            reportService.reportPost(report);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok("Báo cáo thành công");
            return apiResponse;}
            else
            {
                ApiResponse apiResponse = new ApiResponse();
                apiResponse.ok("Đã báo cáo rồi!");
                return apiResponse;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PostMapping("/users/{userid}")
    public ApiResponse reportUser(@PathVariable int userid, @RequestBody ReportRequest reportRequest) {
        try {

            {
            User user = new User();
            user.setId(userid);
            User userReporter = new User();
            userReporter.setId(reportRequest.getCreateById());
            Report report=new Report();
            report.setReportedUserID(user);
            report.setReporterID(userReporter);
            report.setReason(reportRequest.getContent_report());
            reportService.reportPost(report);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok("Báo cáo thành công");
            return apiResponse;}
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
}

