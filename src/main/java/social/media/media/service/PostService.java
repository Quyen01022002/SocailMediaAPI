package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.PostResponse;

import java.util.List;

public interface PostService {
    public PostResponse addPost(Post post, List<pictureOfPost> listImg);
    public PostResponse updatePost(Post post);

    public Void Delete(Post post);
}
