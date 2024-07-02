package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.*;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.model.request.HotPostRequest;
import social.media.media.model.request.PostRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PostMapper {
    pictureOfPostMapper INSTANCE = Mappers.getMapper( pictureOfPostMapper.class );

    @Mapping(source = "groups.id", target = "groupid")
    @Mapping(source = "groups.name", target = "groupname")
    @Mapping(source = "pageMemberships", target = "saveItemList")
    @Mapping(expression = "java(post.getPageMemberships() != null ? post.getPageMemberships().size() : 0)", target = "save_count")
    PostResponse toResponse(Post post);
    default Post toEnity(PostRequest post){
        Post entity = new Post();
        entity.setContentPost(post.getContentPost());
        if(post.getGroups()!=0)
        {
            Groups groups=new Groups();
            groups.setId(post.getGroups());
            entity.setGroups(groups);
        } if(post.getClasses()!=0)
        {
            Classes classes=new Classes();
            classes.setId(post.getClasses());
            entity.setClasses(classes);
        }
        if(post.getClasses()!=0)
        {
            Save groups=new Save();
            groups.setId(post.getClasses());
            entity.setPage(groups);
        }
        if(post.getSector()!= 0)
        {
            Sector sector = new Sector();
            sector.setId(post.getSector());
            entity.setSectors(sector);
        }
        entity.setStatus(post.getStatus());
        entity.setListAnh(INSTANCE.toEntity(post.getListAnh()));
        return entity;
    }
    default Post HotToEnity(HotPostRequest post){
        Post entity = new Post();
        entity.setContentPost(post.getContentPost());
        if(post.getGroups()!=0)
        {
            Groups groups=new Groups();
            groups.setId(post.getGroups());
            entity.setGroups(groups);
        } if(post.getClasses()!=0)
        {
            Classes classes=new Classes();
            classes.setId(post.getClasses());
            entity.setClasses(classes);
        }
        if(post.getClasses()!=0)
        {
            Save groups=new Save();
            groups.setId(post.getClasses());
            entity.setPage(groups);
        }
        if(post.getSector()!= 0)
        {
            Sector sector = new Sector();
            sector.setId(post.getSector());
            entity.setSectors(sector);
        }
        entity.setStatus(post.getStatus());
        entity.setHotinday(post.getHotinday());
        entity.setListAnh(INSTANCE.toEntity(post.getListAnh()));
        return entity;
    }

    List<PostResponseDTO> toResponseDTO(List<Post> post);

    List<PostResponse> toResponseList(List<Post> post);
}
