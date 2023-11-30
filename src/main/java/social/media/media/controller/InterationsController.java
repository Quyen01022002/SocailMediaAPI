package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.*;
import social.media.media.service.InterationService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Interations")
@RequiredArgsConstructor
public class InterationsController {
    @Autowired
    InterationService interationService;

    @PostMapping("/")
    public ApiResponse<LikeResponse> post(@RequestBody interations like ) {

        LikeResponse savedpost = interationService.Like(like);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }

}
