package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.*;
import social.media.media.model.reponse.*;
import social.media.media.model.request.*;
import social.media.media.service.ClassService;
import social.media.media.service.GroupService;
import social.media.media.service.MessageService;
import social.media.media.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    ClassMembersMapper classMembersMapper;
    @Autowired
    social.media.media.service.friendsService friendsService;
    @Autowired
    FriendsMapper friendsMapper;

    @PostMapping("/")
    public ApiResponse<ClassResponse> post(@RequestBody ClassRequest classRequest) {
        Classes groups1 = classMapper.classRequestToEntity(classRequest);
        ClassResponse savedPage = classService.addClasses(groups1);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedPage);
        return apiResponse;
    }

    @PostMapping("/addMembers")
    public ApiResponse addMembers(@RequestBody List<ClassMemberRequest> user) {
        try {

            List<ClassMembers> groupMembers =classMembersMapper.toEntityList(user);
            for(ClassMembers item: groupMembers)
            {

                classService.addMemberClass(item);

            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @GetMapping("/teacher/{id}")
    public ApiResponse<List<ClassResponse>> ListGroupAdmin(@PathVariable int id) {
        try {

            List<ClassResponse> profile = classService.ListGroupsAdmin(id);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }


    @DeleteMapping("/members/{classMemberId}")
    public ApiResponse DeleteMembers(@PathVariable int classMemberId) {
        try {

            classService.DeleteMembers(classMemberId);
            System.out.println(classMemberId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

    @GetMapping("/{id}")
    public ApiResponse<ClassResponse> getprofile(@PathVariable int id) {
        try {

            ClassResponse pageDetail = classService.Detail(id);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(pageDetail);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

}
