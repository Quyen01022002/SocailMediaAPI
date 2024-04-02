package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.*;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.model.reponse.UserResponse;
import social.media.media.model.request.PostRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PostMapper {
    pictureOfPostMapper INSTANCE = Mappers.getMapper( pictureOfPostMapper.class );
    @Mapping(source = "groups.id", target = "groupid")
    PostResponse toResponse(Post post);
    ;
    default Post toEnity(PostRequest post){
        Post entity = new Post();
        entity.setContentPost(post.getContentPost());
        if(post.getGroups()!=0)
        {
            Groups groups=new Groups();
            groups.setId(post.getGroups());
            entity.setGroups(groups);
        }
        if(post.getPage()!=0)
        {
            page groups=new page();
            groups.setId(post.getPage());
            entity.setPage(groups);
        }

        entity.setListAnh(INSTANCE.toEntity(post.getListAnh()));
        return entity;
    }

    List<PostResponseDTO> toResponseDTO(List<Post> post);
    List<PostResponse> toResponseList(List<Post> post);
}
