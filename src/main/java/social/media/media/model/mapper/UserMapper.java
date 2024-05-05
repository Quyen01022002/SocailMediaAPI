package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.User;
import social.media.media.model.reponse.UserResponse;
import social.media.media.model.reponse.UserResponsePost;
import social.media.media.model.request.UserRequest;
import social.media.media.model.request.UserRequest2;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User userEntity);
    UserResponsePost toResponsePost(User userEntity);
    User toEntity(UserRequest userEntity);
    User toEntity2(UserRequest2 userEntity);
    List<UserResponse> toResponseList(List<User> user);

}
