package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.service.friendsService;

import java.util.List;
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {
    @Autowired
    friendsService friendsService;

    @GetMapping("/{id}")
    public ApiResponse<List<FriendsResponse>> getfriendlist(@PathVariable int id)
    {

        try {

            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByUser(user,true);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(list);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/invate/{id}")
    public ApiResponse<List<FriendsResponse>> getfriendInvitelist(@PathVariable int id)
    {

        try {

            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByUser(user,false);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(list);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/addfriend")
    public ApiResponse<FriendsResponse> addfriend(@RequestBody friends friends)
    {

        try {


            FriendsResponse saved=friendsService.addFriend(friends);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(saved);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/accept")
    public ApiResponse<FriendsResponse> acceptFriend(@RequestBody friends friends)
    {

        try {


            FriendsResponse saved=friendsService.acceptFriend(friends);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(saved);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @DeleteMapping("/unfriend")
    public ApiResponse unfriend(@RequestBody friends friends)
    {
        try {
           friendsService.Delete(friends);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
}
