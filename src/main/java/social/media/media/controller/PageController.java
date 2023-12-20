package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.reponse.*;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.model.request.GroupsRequest;
import social.media.media.model.request.PageAdminRequest;
import social.media.media.service.PageService;
import social.media.media.service.PostService;
import social.media.media.service.UserService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        //Hàm này là xem danh sách Page mà người dùng hiện tại đang theo dõi
        try {

            List<PageResponse> pages = pageService.getPageFollow(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(pages);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/admin/{id}")
    public ApiResponse<List<PageMembersResponse>> ListPageAdmin(@PathVariable int id) {
        //Hàm này là xem danh sách Page mà người dùng hiện tại tạo ra(đang là admin)
        try {

            List<PageResponse> pages = pageService.getPageAdmin(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(pages);
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
    public ApiResponse DeleteMembers(@RequestParam("pageId") int groupid,@RequestParam("userId") int userId) {
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

    @GetMapping("/")
    public ApiResponse<List<PageResponse>> getAllPages() {
        try {
            List<PageResponse> allPages = pageService.getAllPages();
            ApiResponse<List<PageResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(allPages);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Xử lý các ngoại lệ khác
        }
    }
    @PutMapping("/updateAdmin/{id}")
    public ApiResponse<PostResponse> UpdateAdminGroup(@PathVariable int id,@RequestBody PageAdminRequest groupsRequest) {
        PageResponse savedpost = pageService.updateAdminPage(groupsRequest);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }


    @GetMapping("/getFollowBool")
    public ApiResponse<PostResponse> getBoolFollow(@RequestParam("pageId") int pageid,@RequestParam("userId") int userId){
        try {
            PageMembersResponse pageMembersResponse = pageService.getBoolFollow(pageid, userId);

            ApiResponse apiResponse = new ApiResponse();
            if (pageMembersResponse != null)
            {apiResponse.ok(pageMembersResponse);
            return apiResponse;}
            return null;

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/{pageid}/follow")
    public ApiResponse<List<PageMembersResponse>> ListFollowPage(@PathVariable int pageid) {
        //hàm này là xem danh sách nguời dùng đang theo dõi page
        try {

            List<PageMembersResponse> pages = pageService.getFollowPage(pageid);
            int length = pages.size();

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(length);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }



}
