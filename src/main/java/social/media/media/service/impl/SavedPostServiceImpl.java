package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.repository.PostIimageRepository;
import social.media.media.repository.PostRepository;
import social.media.media.repository.SavedPostRepository;
import social.media.media.service.PostService;
import social.media.media.service.SavedPostService;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedPostServiceImpl implements SavedPostService {

    private final SavedPostRepository savedPostRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostIimageRepository postIimageRepository;


    @Override
    public PostResponse savedPost(SavedPost post) {
        try {

            SavedPost savedPost=savedPostRepository.saveAndFlush(post);

            return postMapper.toResponse(savedPost.getPost());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public PostResponse onePost(int id) {
        return null;
    }

    @Override
    public List<PostResponseDTO> updatePost(int id) {
        User user=new User();
        user.setId(id);
        List<SavedPost> list = savedPostRepository.findByUser(user);
        List<PostResponse> ListFriendResponse=new ArrayList<>();
        List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
        for(SavedPost item:list)
        {

            PostResponse fp=postMapper.toResponse(item.getPost());
            PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
            itemPostResponseDTO.setSavedId(item.getId());
            itemPostResponseDTO.setId(fp.getId());
            itemPostResponseDTO.setComment_count(fp.getLisCmt().size());
            itemPostResponseDTO.setCreateBy(fp.getCreateBy());
            itemPostResponseDTO.setLike_count(fp.getListLike().size());
            itemPostResponseDTO.setContentPost(fp.getContentPost());
            itemPostResponseDTO.setTimeStamp(fp.getTimeStamp());
            for(LikeResponse itemlike:  fp.getListLike())
            {
                if(itemlike.getCreateBy().getId()==id)
                {
                    itemPostResponseDTO.setUser_liked(true);

                }
                else {
                    itemPostResponseDTO.setUser_liked(false);
                }
            }
            itemPostResponseDTO.setListAnh(fp.getListAnh());
            itemPostResponseDTO.setStatus(fp.getStatus());

            postResponseDTOList.add(itemPostResponseDTO);
        }




        return postResponseDTOList;
    }

    @Override
    public void Delete(int id) {

    }
}
