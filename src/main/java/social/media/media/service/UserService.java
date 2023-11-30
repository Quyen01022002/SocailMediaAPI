package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.userFollow;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;

public interface UserService {
    public UserResponse profile(int id);
    public void Follow(User user);
    public void unFollow(userFollow user);
    public void updateAvatar(int id,String Avatar);
    public void update(int id, User user);
}
