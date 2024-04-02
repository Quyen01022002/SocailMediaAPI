package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.User;
import social.media.media.model.entity.UserLevel;
import social.media.media.model.entity.friends;
import social.media.media.model.entity.userFollow;
import social.media.media.model.enums.GenderEnum;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.FriendsResponseDTO;
import social.media.media.model.reponse.UserResponse;
import social.media.media.repository.UserFollowRepository;
import social.media.media.repository.UserLevelRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.UserLevelService;
import social.media.media.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLevelServiceImpl implements UserLevelService {

    private final UserLevelRepository userLevelRepository;



    @Override
    public List<UserLevel> findAll() {
        List<UserLevel> list=userLevelRepository.findAllByOrderByMaxPointsAsc();
        return list;
    }
}
