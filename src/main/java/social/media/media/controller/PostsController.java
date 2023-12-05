package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.PostRequest;
import social.media.media.service.PostService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;

@RestController
    @RequestMapping("/post")
@RequiredArgsConstructor
public class PostsController {
    @Autowired
    friendsService friendsService;
    @Autowired
    PostService postService;
    @Autowired
    PostMapper postMapper;
    @GetMapping("/{id}")
    public ApiResponse<List<PostResponseDTO>> getfriendlist(@PathVariable int id)
    {

        try {

            User user=new User();
            user.setId(id);
            List<FriendsResponse> list=friendsService.findByUser(user,true);
            List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
            for(FriendsResponse item:list)
            {
                for(PostResponse itempost:item.getUser().getPostList())
                {
                    PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                    itemPostResponseDTO.setId(itempost.getId());
                    itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                    itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                    itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                    itemPostResponseDTO.setContentPost(itempost.getContentPost());
                    itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                    for(LikeResponse itemlike:  itempost.getListLike())
                    {
                        if(itemlike.getCreateBy().getId()==id)
                        {
                            itemPostResponseDTO.setUser_liked(true);

                        }
                        else {
                            itemPostResponseDTO.setUser_liked(false);
                        }
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());

                    postResponseDTOList.add(itemPostResponseDTO);
                }
                for(PostResponse itempost:item.getFriend().getPostList())
                {
                    PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                    itemPostResponseDTO.setId(itempost.getId());
                    itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                    itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                    itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                    itemPostResponseDTO.setContentPost(itempost.getContentPost());
                    itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                    for(LikeResponse itemlike:  itempost.getListLike())
                    {
                        if(itemlike.getCreateBy().getId()==id)
                        {
                            itemPostResponseDTO.setUser_liked(true);

                        }
                        itemPostResponseDTO.setUser_liked(false);
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());

                    postResponseDTOList.add(itemPostResponseDTO);
                }


            }
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.ok(postResponseDTOList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @PostMapping("/post")
        public ApiResponse<PostResponse> post(@RequestBody PostRequest post) {
        Post postEnity=postMapper.toEnity(post);
        PostResponse savedpost = postService.addPost(postEnity,postEnity.getListAnh());

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.ok(savedpost);
        return apiResponse;
    }
}
