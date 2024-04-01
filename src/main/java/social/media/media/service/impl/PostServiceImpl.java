package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.exception.ValidationException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.entity.pictureOfPost;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.repository.PostIimageRepository;
import social.media.media.repository.PostRepository;
import social.media.media.repository.friendsRepository;
import social.media.media.service.PostService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostIimageRepository postIimageRepository;

    @Override
    public PostResponse addPost(Post post, List<pictureOfPost> listImg) {
        try {

            Post savedPost=postRepository.saveAndFlush(post);
            for(pictureOfPost item:listImg)
            {
                item.setListAnh(savedPost);
                postIimageRepository.saveAndFlush(item);
            }

            // Map to Response
            return postMapper.toResponse(post);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public PostResponse updatePost(Post post, List<pictureOfPost> listImg) {
        try {
            Post exPost = postRepository.findById(post.getId()).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (exPost == null) {
                throw new NotFoundException("Not Found");
            }


            exPost.setContentPost(post.getContentPost());
            exPost.setListAnh(listImg);
            // Update
            postRepository.saveAndFlush(exPost);

            // Map to Response
            return postMapper.toResponse(post);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public List<PostResponse> getTop10(){
        PageRequest pageable = PageRequest.of(0, 10);
        List<Post> result = postRepository.findTop10PostsByLikes(pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        return listPost;
    }
    @Override
    public List<PostResponse> searchPost(String keyword){
        List<Post> result = postRepository.findByContentPostContaining(keyword);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        return listPost;
    }

    @Override
    public Void Delete(int id) {
        try {
            Post friends1 = postRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            if (friends1 == null) {
                throw new NotFoundException(" Not Found");
            }


            postRepository.delete(friends1);
        } catch (ApplicationException ex) {
            throw ex;
        }
        return null;
    }
}
