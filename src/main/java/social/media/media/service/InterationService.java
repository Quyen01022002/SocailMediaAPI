package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.interations;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;

public interface InterationService {
    public LikeResponse Like(interations like);

}
