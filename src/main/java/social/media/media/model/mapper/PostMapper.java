package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.Groups;
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
        if(post.getGroups()!=0)
        {
            Groups groups=new Groups();
            groups.setId(post.getGroups());
            entity.setGroups(groups);
        }
        entity.setListAnh(INSTANCE.toEntity(post.getListAnh()));
        return entity;
    }
}
