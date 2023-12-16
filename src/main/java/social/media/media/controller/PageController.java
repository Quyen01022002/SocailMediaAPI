package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.reponse.*;
import social.media.media.model.request.GroupsRequest;
import social.media.media.service.PageService;
import social.media.media.service.PostService;
import social.media.media.service.UserService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {
    @Autowired
    PageService pageService;
    @Autowired
    UserService userService;


    @PostMapping("/")
    public ApiResponse<PageResponse> post(@RequestBody page page) {

        PageResponse savedPage = pageService.addPage(page);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedPage);
        return apiResponse;
    }

    @DeleteMapping("/members/")
    public ApiResponse DeleteMembers(@RequestBody PageMembers friends) {
        try {
            pageService.DeleteMembers(friends);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @DeleteMapping("/")
    public ApiResponse DeletePage(@RequestBody page page) {
        try {
            page isDeleted = pageService.Delete(page);
            if (isDeleted.getGroupMembers() != null) {
                for (PageMembers item : isDeleted.getGroupMembers()) {
                    pageService.DeleteMembers(item);
                }
            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @GetMapping("/{id}")
    public ApiResponse<PageResponse> getprofile(@PathVariable int id) {
        try {

            PageResponse pageDetail = pageService.Detail(id);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(pageDetail);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @GetMapping("/follow/{id}")
    public ApiResponse<List<PageMembersResponse>> ListPageFollow(@PathVariable int id) {
        try {

            List<PageMembersResponse> profile = pageService.ListPage(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PostMapping("/addMembers")
    public ApiResponse addMembers(@RequestParam("userId") int user, @RequestParam("pageId") int page) {
        try {
            PageMembers pageMembers = new PageMembers();
            page p = new page();
            User u = new User();
            p.setId(page);
            u.setId(user);
            pageMembers.setPage(p);
            pageMembers.setUser(u);
            pageService.addMemberPage(pageMembers);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PutMapping("/update/{id}")
    public ApiResponse<PostResponse> UpdatePage(@PathVariable int id,@RequestBody page page) {
        PageResponse savedPage = pageService.updatePage(page);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedPage);
        return apiResponse;
    }

    @DeleteMapping("/unfollow")
    public ApiResponse DeleteMembers(@RequestParam("groupID") int groupid,@RequestParam("userID") int userId) {
        try {
            PageMembers groupMembers=new PageMembers();
            User user=new User();
            user.setId(userId);
            page groups = new page();
            groups.setId(groupid);
            groupMembers.setPage(groups);
            groupMembers.setUser(user);
            pageService.DeleteMembers(groupMembers);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

}
