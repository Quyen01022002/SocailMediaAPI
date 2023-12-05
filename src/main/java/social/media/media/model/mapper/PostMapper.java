package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;
import social.media.media.model.request.PostRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PostMapper {
    pictureOfPostMapper INSTANCE = Mappers.getMapper( pictureOfPostMapper.class );
    PostResponse toResponse(Post post);
    ;
    default Post toEnity(PostRequest post){
        Post entity = new Post();
        entity.setContentPost(post.getContentPost());
        entity.setListAnh(INSTANCE.toEntity(post.getListAnh()));
        return entity;
    }
}
