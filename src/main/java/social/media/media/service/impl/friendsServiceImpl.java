package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.exception.ValidationException;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.entity.interations;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.repository.UserRepository;
import social.media.media.repository.friendsRepository;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class friendsServiceImpl implements friendsService {

    private final friendsRepository friendsRepository;
    @Autowired
    FriendsMapper friendsMapper;
    @Override
    public List<FriendsResponse> findByUser(User user,Boolean Status) {

        List<friends> list = friendsRepository.findByUserOrFriendAndStatus(user,user,Status);
        List<FriendsResponse> ListFriendResponse=new ArrayList<>();
        for(friends item:list)
        {

            FriendsResponse fp=friendsMapper.toResponse(item);
            fp.getFriend().setCountFriend(item.getFriend().countMutualFriends(item.getUser()));
            ListFriendResponse.add(fp);
        }


        return ListFriendResponse;
    }

    @Override
    public FriendsResponse addFriend(friends friends) {
        try {


            // Save
            friends friends1 = friendsRepository.save(friends);

            // Response
            FriendsResponse friendsResponse=friendsMapper.toResponse(friends1);

            return friendsResponse;
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (ValidationException ex) {
            throw ex; // Rethrow ValidationException
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }
    }

    @Override
    public Void Delete(int id) {
        try {
            friends friends1 = friendsRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (friends1 == null) {
                throw new NotFoundException("Product Not Found");
            }


            friendsRepository.delete(friends1);
        } catch (ApplicationException ex) {
            throw ex;
        }
        return null;
    }

    @Override
    public FriendsResponse acceptFriend(int id ) {
        try {
            friends existingfriend = friendsRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (existingfriend == null) {
                throw new NotFoundException("Not Found");
            }

            existingfriend.setStatus(true);

            // Update
            friendsRepository.saveAndFlush(existingfriend);

            // Map to Response
            return friendsMapper.toResponse(existingfriend);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

}
