package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.notications;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.reponse.NoticationsResponse;
import social.media.media.model.reponse.PostResponse;

import java.util.List;

public interface NoticationService {
    public NoticationsResponse addNotication(notications notications);
    public List<NoticationsResponse> listNotication(int id);
    public NoticationsResponse updateNotications(notications post);

    public Void Delete(notications post);
}
