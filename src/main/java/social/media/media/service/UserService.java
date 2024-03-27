package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.userFollow;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;

import java.util.List;

public interface UserService {
    public UserResponse profile(int id);
    public void Follow(User user);
    public List<UserResponse> List20Follow();
    public List<UserResponse> Search(String key);
    public void unFollow(userFollow user);
    public void updateAvatar(int id,String Avatar);
    public void updateBackGround(int id,String Avatar);
    public void fcm(int id,String fcm);
    public void update(int id, User user);
    public void update2(int id, User user);

    public Boolean checkEmail(String email);
}
