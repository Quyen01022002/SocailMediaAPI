package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.GroupMapper;
import social.media.media.model.mapper.GroupsMembersMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.GroupsMenberRequest;
import social.media.media.model.request.GroupsRequest;
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
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    GroupsMembersMapper groupsMembersMapper;


    @PostMapping("/")
    public ApiResponse<GroupsResponse> post(@RequestBody GroupsRequest groups) {
        Groups groups1 = groupMapper.toEntity(groups);
        GroupsResponse savedPage = groupService.addPage(groups1);

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
    public ApiResponse addMembers(@RequestBody List<GroupsMenberRequest> user) {
        try {
            List<GroupMembers> groupMembers =groupsMembersMapper.toEntityList(user);
            for(GroupMembers item: groupMembers)
            {
                groupService.addMemberPage(item);

            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
}
