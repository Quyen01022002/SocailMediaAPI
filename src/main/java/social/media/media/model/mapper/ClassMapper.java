package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.Groups;
import social.media.media.model.reponse.ClassResponse;
import social.media.media.model.reponse.GroupsResponse;
import social.media.media.model.request.ClassRequest;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.model.request.GroupsRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ClassMapper {
    @Mapping(source = "groups.id", target = "groups")
    ClassResponse toResponse(Classes Groups);
    @Mapping(source = "groups", target = "groups.id")
    Classes toEntity(ClassResponse Groups);

    @Mapping(source = "groups", target = "groups.id")
    @Mapping(source = "avatar", target = "Avatar")
    @Mapping(source = "backavatar", target = "BackAvatar")
    Classes classRequestToEntity(ClassRequest classRequest);
    List<ClassResponse> toResponseList(List<Classes> Groups);
}
