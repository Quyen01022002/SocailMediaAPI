package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.reponse.*;
import social.media.media.service.GroupService;
import social.media.media.service.PageService;
import social.media.media.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupsController {
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;


    @PostMapping("/")
    public ApiResponse<GroupsResponse> post(@RequestBody Groups groups) {

        GroupsResponse savedPage = groupService.addPage(groups);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedPage);
        return apiResponse;
    }

    @DeleteMapping("/members/")
    public ApiResponse DeleteMembers(@RequestBody GroupMembers friends) {
        try {
            groupService.DeleteMembers(friends);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @DeleteMapping("/")
    public ApiResponse DeletePage(@RequestBody Groups page) {
        try {
            Groups isDeleted = groupService.Delete(page);
            if (isDeleted.getGroupMembers() != null) {
                for (GroupMembers item : isDeleted.getGroupMembers()) {
                    groupService.DeleteMembers(item);
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
    public ApiResponse<GroupsResponse> getprofile(@PathVariable int id) {
        try {

            GroupsResponse pageDetail = groupService.Detail(id);

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

            List<GroupsMembersResponse> profile = groupService.ListGroups(id);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @PostMapping("/addMembers")
    public ApiResponse addMembers(@RequestParam("userId") int user, @RequestParam("groupId") int page) {
        try {
            GroupMembers groupMembers = new GroupMembers();
            Groups g = new Groups();
            User u = new User();
            g.setId(page);
            u.setId(user);
            groupMembers.setGroup(g);
            groupMembers.setUser(u);
            groupService.addMemberPage(groupMembers);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
}
