package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.UserResponse;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface FriendsMapper {

    FriendsResponse toResponse(friends friendsEntity);
    List<FriendsResponse> toResponseList(List<friends> friendsList);

}
