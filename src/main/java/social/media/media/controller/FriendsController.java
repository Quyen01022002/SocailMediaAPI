package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.FriendsResponseDTO;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {
    @Autowired
    friendsService friendsService;
     @Autowired
     FriendsMapper friendsMapper;


    @GetMapping("/{id}")
    public ApiResponse<List<FriendsResponseDTO>> getfriendlist(@PathVariable int id)
    {

        try {

            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByUser(user,true);
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();
            for(FriendsResponse item:list)
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();
                if(user.getId()==item.getFriend().getId())
                {
                    friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getFriend());
                }
              else {
                    friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getUser());

                }
              friends isFriend=friendsService.isFriend(user,item.getFriend().getId());
                if(isFriend!=null)
                {
                    friendsResponseDTO.setIsFriends(true);

                }
                else {
                    friendsResponseDTO.setIsFriends(false);

                }
                friendsResponseDTO.setIdFriends(item.getId());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(friendsResponseDTOList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/invate/{id}")
    public ApiResponse<List<FriendsResponseDTO>> getfriendInvitelist(@PathVariable int id)
    {

        try {

            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByFriend(user,false);
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();
            for(FriendsResponse item:list)
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();
                friendsResponseDTO=friendsMapper.toFriendsResponseDto(item.getFriend());

                friendsResponseDTO.setIdFriends(item.getId());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(friendsResponseDTOList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/addfriend")
    public ApiResponse<FriendsResponse> addfriend(@RequestParam("userId") int userId,@RequestParam("friendsId") int friendid)
    {

        try {

        	friends friends=new friends();
        	User u1=new User();
        	u1.setId(userId);
        	User u2=new User();
        	u2.setId(friendid);
        	friends.setUser(u1);
        	friends.setFriend(u2);
        	friends.setStatus(false);
            FriendsResponse saved=friendsService.addFriend(friends);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(saved);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/accept/{id}")
    public ApiResponse<FriendsResponse> acceptFriend(@PathVariable int id)
    {

        try {


            FriendsResponse saved=friendsService.acceptFriend(id);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(saved);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @DeleteMapping("/unfriend")
    public ApiResponse unfriend(@RequestParam("userId") int userId,@RequestParam("friendsId") int friendid)
    {
        try {
           friendsService.Delete(userId,friendid);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
}
