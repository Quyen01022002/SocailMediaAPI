package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.exception.ValidationException;
import social.media.media.model.entity.*;
import social.media.media.model.enums.StatusCmtPostEnum;
import social.media.media.model.enums.StatusViewPostEnum;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.InterationsMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.*;
import social.media.media.repository.*;
import social.media.media.service.PostService;
import social.media.media.service.UserService;
import social.media.media.service.friendsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @Override
    public PostResponse addPost(Post post, List<pictureOfPost> listImg) {
        try {
            List<UserProgress> userProgressList = userService.loadListTeacherProgressInSector(0,post.getGroups().getId(),post.getSectors().getId());
            User user = new User();
            if (userProgressList.size()!=0){
                UserProgress minUserProgress = userProgressList.get(0);
                for (UserProgress userProgress : userProgressList) {
                    if (userProgress.getCountAllPostReply() < minUserProgress.getCountAllPostReply()) {
                        minUserProgress = userProgress;
                    }
                }
                user.setId(minUserProgress.getUserResponse().getId());
                post.setUserReply(user);
            }
            Post savedPost = postRepository.saveAndFlush(post);
            for (pictureOfPost item : listImg) {
                item.setListAnh(savedPost);
                postIimageRepository.saveAndFlush(item);
            }

            // Map to Response
            return postMapper.toResponse(savedPost);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public PostResponse addPostImportant(Post post, List<pictureOfPost> listImg) {
        try {
            Post savedPost = postRepository.saveAndFlush(post);
            for (pictureOfPost item : listImg) {
                item.setListAnh(savedPost);
                postIimageRepository.saveAndFlush(item);
            }

            // Map to Response
            return postMapper.toResponse(savedPost);
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
        PostResponse postResponse = postMapper.toResponse(exPost);
        postResponse.setCreateBy(userMapper.toResponsePost(exPost.getCreateBy()));
        return postResponse;
    }

    @Override
    public List<PostResponse> getTop10() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Post> result = postRepository.findTop10PostsByLikes(pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }

    @Override
    public List<PostResponse> getTop10Teacher(int adminId) {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Post> result = postRepository.findTop10ByTeacherIdOrderByLikesDesc(adminId, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }

    @Override
    public List<PostResponse> searchPost(String keyword, int pagenumber) {
        PageRequest pageable = PageRequest.of(pagenumber, 10);
        StatusViewPostEnum statusViewPostEnum = StatusViewPostEnum.ONLYME;
        List<Post> result = postRepository.findByContentPostContaining(keyword, statusViewPostEnum, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        return listPost;
    }

    @Override
    public List<IntactionResponse> getAllMyLike(int userid) {
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

    @Override
    public List<PostResponse> getPostClass(int userid, int pagenumber) {
        PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> result = postRepository.findPostsByUserId(userid, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }
    @Override
    public List<PostResponse> getMyPost(int userid, int pagenumber) {
        PageRequest pageable = PageRequest.of(pagenumber, 50);
        List<Post> result = postRepository.findPostsByCreateById(userid, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }
    @Override
    public List<PostResponse> getOtherPost(int userid, int pagenumber) {
        PageRequest pageable = PageRequest.of(pagenumber, 50);
        List<Post> result = postRepository.findPostsByCreateByIdOther(userid, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }

    @Override
    public List<PostResponse> getTop5OnMonth(int userid) {
        PageRequest pageable = PageRequest.of(0, 5);
        List<Post> result = postRepository.findTop5PostsByAdminIdAndCurrentMonth(userid, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        return listPost;
    }

    @Override
    public String getPostCountStringByMonthAndAdminId(int adminId) {
        List<Map<String, Object>> postCounts = postRepository.findPostCountByMonthAndAdminId(adminId);

        // Tạo một danh sách chứa số lượng bài viết của từng tháng, mặc định là 0
        Long[] postCountArray = new Long[12]; // 12 tháng trong năm
        Arrays.fill(postCountArray, 0L);

        // Đặt số lượng bài viết thực tế vào vị trí tương ứng của tháng trong mảng
        for (Map<String, Object> map : postCounts) {
            int month = (int) map.get("month"); // Lấy giá trị month
            Long postCount = ((Number) map.get("postCount")).longValue(); // Chuyển đổi từ Integer sang Long
            postCountArray[month - 1] = postCount; // Tháng bắt đầu từ 1, mảng bắt đầu từ 0
        }

        // Chuyển mảng số lượng bài viết thành chuỗi kết quả, ngăn cách bằng dấu phẩy
        String postCountString = Arrays.stream(postCountArray)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return postCountString;
    }

    @Override
    public List<PostResponseDTO> getPostOfAllGroupFollow(int iduser, int pagenumber) {
        PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> list = postRepository.findAllPostsFromFollowedGroups(iduser, StatusViewPostEnum.ONLYME, pageable);
        List<PostResponse> postResponseList = postMapper.toResponseList(list);
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == iduser) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                } else {
                    itemPostResponseDTO.setUser_liked(false);
                }
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            postResponseDTOList.add(itemPostResponseDTO);
        }



        return postResponseDTOList;
    }

    @Override
    public List<PostResponseDTO> getPostReply(int iduser, int pagenumber) {
        PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> list = postRepository.findAllByUserReplyId(iduser, pageable);
        List<PostResponse> postResponseList = postMapper.toResponseList(list);
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == iduser) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                } else {
                    itemPostResponseDTO.setUser_liked(false);
                }
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            postResponseDTOList.add(itemPostResponseDTO);
        }



        return postResponseDTOList;
    }

    @Override
    public PostResponse notSectorMe(int postid){

        try {
            Post exPost = postRepository.findById(postid).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (exPost == null) {
                throw new NotFoundException("Not Found");
            }


            exPost.setUserReply(null);
            // Update
            Post post = postRepository.saveAndFlush(exPost);

            // Map to Response
            return postMapper.toResponse(post);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }
    @Override
    public List<PostResponseDTO> getPostNotReply(int teacherid) {
        //PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> list = postRepository.findPostsByUserReplyWithUnansweredComments(teacherid);
        List<PostResponse> postResponseList = postMapper.toResponseList(list);
        for (int i=0; i< list.size(); i++){
            postResponseList.get(i).setCreateBy(userMapper.toResponsePost(list.get(i).getCreateBy()));
        }
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == teacherid) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                } else {
                    itemPostResponseDTO.setUser_liked(false);
                }
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            postResponseDTOList.add(itemPostResponseDTO);
        }

        return postResponseDTOList;
    }


    @Override
    public List<PostResponseDTO> loadPostReportedInGroup(int pagenumber, int groupid) {
        PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> result = postRepository.findReportedPostsInGroup(groupid,pageable);
        List<PostResponse> postResponseList = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            postResponseList.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : postResponseList) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == result.get(0).getGroups().getAdminId().getId()) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                } else {
                    itemPostResponseDTO.setUser_liked(false);
                }
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());
            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            postResponseDTOList.add(itemPostResponseDTO);
        }

        return postResponseDTOList;
    }

    @Override
    public PostResponseDTO blockCmt(int postid){
        Post exPost = postRepository.findById(postid).orElseThrow(() -> new NotFoundException("friend Not Found"));
        exPost.setStatusCmtPostEnum(StatusCmtPostEnum.NOTUSER);
        postRepository.saveAndFlush(exPost);
    PostResponse itempost = postMapper.toResponse(exPost);
    itempost.setCreateBy(userMapper.toResponsePost(exPost.getCreateBy()));
        PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
        itemPostResponseDTO.setId(itempost.getId());
        itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
        itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
        itemPostResponseDTO.setLike_count(itempost.getListLike().size());
        itemPostResponseDTO.setContentPost(itempost.getContentPost());
        itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
        itemPostResponseDTO.setGroupid(itempost.getGroupid());
        for (LikeResponse itemlike : itempost.getListLike()) {
            if (itemlike.getCreateBy().getId() == exPost.getCreateBy().getId()) {
                itemPostResponseDTO.setUser_liked(true);
                break;
            } else {
                itemPostResponseDTO.setUser_liked(false);
            }
        }
        itemPostResponseDTO.setListAnh(itempost.getListAnh());
        itemPostResponseDTO.setStatus(itempost.getStatus());
        itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
        itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
            return itemPostResponseDTO;
        }
    @Override
    public PostResponseDTO unblockCmt(int postid){
        Post exPost = postRepository.findById(postid).orElseThrow(() -> new NotFoundException("friend Not Found"));
        exPost.setStatusCmtPostEnum(StatusCmtPostEnum.ALLUSER);
        postRepository.saveAndFlush(exPost);
        PostResponse itempost = postMapper.toResponse(exPost);
        itempost.setCreateBy(userMapper.toResponsePost(exPost.getCreateBy()));
        PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
        itemPostResponseDTO.setId(itempost.getId());
        itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
        itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
        itemPostResponseDTO.setLike_count(itempost.getListLike().size());
        itemPostResponseDTO.setContentPost(itempost.getContentPost());
        itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
        itemPostResponseDTO.setGroupid(itempost.getGroupid());
        for (LikeResponse itemlike : itempost.getListLike()) {
            if (itemlike.getCreateBy().getId() == exPost.getCreateBy().getId()) {
                itemPostResponseDTO.setUser_liked(true);
                break;
            } else {
                itemPostResponseDTO.setUser_liked(false);
            }
        }
        itemPostResponseDTO.setListAnh(itempost.getListAnh());
        itemPostResponseDTO.setStatus(itempost.getStatus());
        itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
        itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
        return itemPostResponseDTO;
    }
}
