package social.media.media.service;

import social.media.media.model.entity.User;
import social.media.media.model.entity.UserLevel;
import social.media.media.model.entity.userFollow;
import social.media.media.model.reponse.UserResponse;

import java.util.List;

public interface UserLevelService {
    public List<UserLevel> findAll();

}
