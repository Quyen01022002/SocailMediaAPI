package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.SavedPost;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;

import java.util.List;

public interface SavedPostService {
    public PostResponse savedPost(SavedPost post);
    public PostResponse onePost(int id);
    public List<PostResponseDTO> updatePost(int id);

    public void Delete(int id);
}
