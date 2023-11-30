package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.userFollow;
import social.media.media.model.reponse.*;
import social.media.media.service.PostService;
import social.media.media.service.UserService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getprofile(@PathVariable int id)
    {
        try {

            UserResponse profile=userService.profile(id);

            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(profile);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @DeleteMapping("/unfollow")
    public ApiResponse DeleteMembers(@RequestBody userFollow friends) {
        try {userService.unFollow(friends);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/follow")
    public ApiResponse Follow(@RequestBody User user)
    {
        try {

            userService.Follow(user);

            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PatchMapping("/{id}")
    public ApiResponse partiallyUpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {


        try {
            String Avatar = (String) updates.get("avatar");
            userService.updateAvatar(id,Avatar);

            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }
    @PutMapping("/{id}")
    public ApiResponse UpdateUser(@PathVariable int id, @RequestBody User updatedUser) {


        try {

            userService.update(id,updatedUser);

            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }
}
