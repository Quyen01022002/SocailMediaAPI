package social.media.media.service;

import social.media.media.model.entity.*;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.IntactionResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;

import java.util.List;

public interface PostService {
    public PostResponse addPost(Post post, List<pictureOfPost> listImg);
    public PostResponse updatePost(Post post, List<pictureOfPost> listImg);
    public PostResponse findOne(int postId);

    public Void Delete(int id);

    public List<PostResponse> getTop10();
    public List<PostResponse> searchPost(String keyword);
    public List<IntactionResponse> getAllMyLike(int userid);
}
