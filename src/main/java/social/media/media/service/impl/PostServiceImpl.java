package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.exception.ValidationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.InterationsMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.IntactionResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.repository.*;
import social.media.media.service.PostService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final InerationsRepository inerationsRepository;
    private final UserRepository userRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostIimageRepository postIimageRepository;
    @Autowired
    InterationsMapper interationsMapper;

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
    public PostResponse findOne(int postId) {
        Post exPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("friend Not Found"));
        return postMapper.toResponse(exPost);
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
    public List<IntactionResponse> getAllMyLike(int userid){
        User user = userRepository.findById(userid).orElseThrow(() -> new NotFoundException(" Not Found"));

        List<interations> result = inerationsRepository.findAllByCreateByOrderByTimeStampDesc(user);
        return interationsMapper.toLResponse(result);
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
