package social.media.media.service;

import org.springframework.stereotype.Service;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.reponse.FriendsResponse;

import java.util.List;
public interface friendsService {
    public List<FriendsResponse> findByUser(User user,Boolean Status);
    public List<FriendsResponse> findByFriend(User user,Boolean Status);
    public FriendsResponse addFriend(friends friends);
    public Void Delete(int user);
    public FriendsResponse acceptFriend(int id);
    public friends isFriend(User u, int f);

}
