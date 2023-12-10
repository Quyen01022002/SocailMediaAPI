package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.NoticationsMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.FriendsResponseDTO;
import social.media.media.model.reponse.NoticationsResponse;
import social.media.media.service.NoticationService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NoticationsController {
    @Autowired
    NoticationService noticationService;
     @Autowired
    NoticationsMapper noticationsMapper;


    @GetMapping("/{id}")
    public ApiResponse<List<NoticationsResponse>> getfriendlist(@PathVariable int id)
    {

        try {

            List<NoticationsResponse> list=noticationService.listNotication(id);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(list);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/read/{id}")
    public ApiResponse<List<NoticationsResponse>> getfriendUnreadlist(@PathVariable int id)
    {

        try {

            List<NoticationsResponse> list=noticationService.listNotiUnRead(id);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(list);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/read/{id}")
    public ApiResponse<NoticationsResponse> acceptFriend(@PathVariable int id)
    {

        try {


            NoticationsResponse saved=noticationService.updateNotications(id);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(saved);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

}
