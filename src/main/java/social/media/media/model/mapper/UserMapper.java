package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.User;
import social.media.media.model.reponse.UserResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User userEntity);
    List<UserResponse> toResponseList(List<User> user);

}
