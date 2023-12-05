package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import social.media.media.model.entity.friends;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.postImgResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface pictureOfPostMapper {

    List<pictureOfPost> toEntity(List<postImgResponse> friendsEntity);


}
