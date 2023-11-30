package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;
import social.media.media.repository.PostRepository;
import social.media.media.repository.UserFollowRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.PostService;
import social.media.media.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    @Autowired
    UserMapper userMapper;


    @Override
    public UserResponse profile(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            return userMapper.toResponse(user);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void Follow(User user) {
        try {
            userFollow userFollow = new userFollow();
            userFollow.setFollowing(user);
            userFollowRepository.saveAndFlush(userFollow);


        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void unFollow(userFollow user) {
        try {
            userFollow friends1 = userFollowRepository.findByFollowerAndFollowing(user.getFollower(),user.getFollowing());
            if (friends1 == null) {
                throw new NotFoundException(" Not Found");
            }


            userFollowRepository.delete(friends1);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public void updateAvatar(int id, String Avatar) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setProfilePicture(Avatar);
        userRepository.saveAndFlush(user);


    }

    @Override
    public void update(int id, User user) {
        User exuser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        exuser.setFirstName(user.getFirstName());
        exuser.setLastName(user.getLastName());
        exuser.setPhone(user.getPhone());
        exuser.setEmail(user.getEmail());
        exuser.setAddress(user.getAddress());
        userRepository.saveAndFlush(exuser);
    }
}
