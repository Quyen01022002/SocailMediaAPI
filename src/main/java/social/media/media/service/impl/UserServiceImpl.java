package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.enums.GenderEnum;
import social.media.media.model.mapper.FriendsMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.ResetPasswordRequest;
import social.media.media.repository.PostRepository;
import social.media.media.repository.UserFollowRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.PostService;
import social.media.media.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FriendsMapper friendsMapper;


    @Override
    public UserResponse profile(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            UserResponse userResponse=userMapper.toResponse(user);
            for (int i=0; i< user.getPostList().size(); i++){
                userResponse.getPostList().get(i).setCreateBy(userMapper.toResponsePost(user.getPostList().get(i).getCreateBy()));
                if (user.getPostList().get(i).getGroups() != null){
                userResponse.getPostList().get(i).setGroupid(user.getPostList().get(i).getGroups().getId());
                userResponse.getPostList().get(i).setGroupname(user.getPostList().get(i).getGroups().getName());}
                userResponse.getPostList().get(i).setStatus(user.getPostList().get(i).getStatus());
                userResponse.getPostList().get(i).setSaveItemList(user.getPostList().get(i).getPageMemberships());

            }
            for (int i=0; i< user.getPostListReply().size(); i++){
                userResponse.getPostListReply().get(i).setCreateBy(userMapper.toResponsePost(user.getPostListReply().get(i).getCreateBy()));
                userResponse.getPostListReply().get(i).setGroupid(user.getPostListReply().get(i).getGroups().getId());
                userResponse.getPostListReply().get(i).setGroupname(user.getPostListReply().get(i).getGroups().getName());
                userResponse.getPostListReply().get(i).setStatus(user.getPostListReply().get(i).getStatus());
                userResponse.getPostListReply().get(i).setSaveItemList(user.getPostListReply().get(i).getPageMemberships());

            }
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();

            for(friends item:user.getFriendships())
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();

                friendsResponseDTO = friendsMapper.toFriendsResponseDto(item.getFriend());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            for(friends item:user.getOtherfriendships())
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();

                friendsResponseDTO = friendsMapper.toFriendsResponseDto(item.getUser());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            userResponse.setFriendships(friendsResponseDTOList);
            return userResponse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    public UserResponse profileByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(" Not Found"));
            UserResponse userResponse=userMapper.toResponse(user);
            for (int i=0; i< user.getPostList().size(); i++){
                userResponse.getPostList().get(i).setCreateBy(userMapper.toResponsePost(user.getPostList().get(i).getCreateBy()));
                if (user.getPostList().get(i).getGroups() != null){
                userResponse.getPostList().get(i).setGroupid(user.getPostList().get(i).getGroups().getId());
                userResponse.getPostList().get(i).setGroupname(user.getPostList().get(i).getGroups().getName());}
                userResponse.getPostList().get(i).setStatus(user.getPostList().get(i).getStatus());
                userResponse.getPostList().get(i).setSaveItemList(user.getPostList().get(i).getPageMemberships());

            }
            for (int i=0; i< user.getPostListReply().size(); i++){
                userResponse.getPostListReply().get(i).setCreateBy(userMapper.toResponsePost(user.getPostListReply().get(i).getCreateBy()));
                userResponse.getPostListReply().get(i).setGroupid(user.getPostListReply().get(i).getGroups().getId());
                userResponse.getPostListReply().get(i).setGroupname(user.getPostListReply().get(i).getGroups().getName());
                userResponse.getPostListReply().get(i).setStatus(user.getPostListReply().get(i).getStatus());
                userResponse.getPostListReply().get(i).setSaveItemList(user.getPostListReply().get(i).getPageMemberships());

            }
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();

            for(friends item:user.getFriendships())
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();

                friendsResponseDTO = friendsMapper.toFriendsResponseDto(item.getFriend());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            for(friends item:user.getOtherfriendships())
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();

                friendsResponseDTO = friendsMapper.toFriendsResponseDto(item.getUser());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            userResponse.setFriendships(friendsResponseDTOList);
            return userResponse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    public UserResponse profileByEmailAndPw(ResetPasswordRequest resetPasswordRequest) {
        try {

            User user = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new NotFoundException(" Not Found"));
            if (passwordEncoder.matches(resetPasswordRequest.getNewPassword(), user.getPassword()))
            {UserResponse userResponse=userMapper.toResponse(user);
                for (int i=0; i< user.getPostList().size(); i++){
                    userResponse.getPostList().get(i).setCreateBy(userMapper.toResponsePost(user.getPostList().get(i).getCreateBy()));
                    if (user.getPostList().get(i).getGroups() != null){
                        userResponse.getPostList().get(i).setGroupid(user.getPostList().get(i).getGroups().getId());
                        userResponse.getPostList().get(i).setGroupname(user.getPostList().get(i).getGroups().getName());}
                    userResponse.getPostList().get(i).setStatus(user.getPostList().get(i).getStatus());
                    userResponse.getPostList().get(i).setSaveItemList(user.getPostList().get(i).getPageMemberships());

                }
                for (int i=0; i< user.getPostListReply().size(); i++){
                    userResponse.getPostListReply().get(i).setCreateBy(userMapper.toResponsePost(user.getPostListReply().get(i).getCreateBy()));
                    userResponse.getPostListReply().get(i).setGroupid(user.getPostListReply().get(i).getGroups().getId());
                    userResponse.getPostListReply().get(i).setGroupname(user.getPostListReply().get(i).getGroups().getName());
                    userResponse.getPostListReply().get(i).setStatus(user.getPostListReply().get(i).getStatus());
                    userResponse.getPostListReply().get(i).setSaveItemList(user.getPostListReply().get(i).getPageMemberships());

                }
            List<FriendsResponseDTO> friendsResponseDTOList=new ArrayList<>();

            for(friends item:user.getFriendships())
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();

                friendsResponseDTO = friendsMapper.toFriendsResponseDto(item.getFriend());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            for(friends item:user.getOtherfriendships())
            {
                FriendsResponseDTO friendsResponseDTO=new FriendsResponseDTO();

                friendsResponseDTO = friendsMapper.toFriendsResponseDto(item.getUser());
                friendsResponseDTOList.add(friendsResponseDTO);
            }
            userResponse.setFriendships(friendsResponseDTOList);
            return userResponse;}
            else return null;

        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void Follow(User user) {
        try {
            userFollow userFollow = new userFollow();
            userFollow.setFollowing(user);
            userFollowRepository.saveAndFlush(userFollow);


        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<UserResponse> List20Follow() {
        List<User> list = userRepository.findRandom20Users();
        List<UserResponse> ListResponse=userMapper.toResponseList(list);

        return ListResponse;
    }

    @Override
    public List<UserResponse> Search(String key) {
        List<User> list = userRepository.searchByNameOrLastNameOrPhoneOrEmail(key);
        List<UserResponse> ListResponse=userMapper.toResponseList(list);

        return ListResponse;
    }

    @Override
    public void unFollow(userFollow user) {
        try {
            userFollow friends1 = userFollowRepository.findByFollowerAndFollowing(user.getFollower(),user.getFollowing());
            if (friends1 == null) {
                throw new NotFoundException(" Not Found");
            }


            userFollowRepository.delete(friends1);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public void updateAvatar(int id, String Avatar) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setProfilePicture(Avatar);
        userRepository.saveAndFlush(user);


    }

    @Override
    public void updatePoint(int id, int point) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setPoint(user.getPoint()+point);
        userRepository.saveAndFlush(user);

    }

    @Override
    public void updateBackGround(int id, String Avatar) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setBackGroundPicture(Avatar);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void fcm(int id, String fcm) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setFcm(fcm);
        userRepository.saveAndFlush(user);


    }
    @Override
    public void update(int id, User user) {
        User exuser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        exuser.setFirstName(user.getFirstName());
        exuser.setLastName(user.getLastName());
        exuser.setDoB(user.getDoB());
        exuser.setAddress(user.getAddress());
        if(user.getGender()==GenderEnum.MALE)
        {
        	exuser.setProfilePicture("http://res.cloudinary.com/dq21kejpj/image/upload/v1701758248/public/cz3abc8wfhtyqkjx0pnq.jpg");
        }
        else
        	
        {
        	exuser.setProfilePicture("http://res.cloudinary.com/dq21kejpj/image/upload/v1701758417/public/lqafeuj7lx8ddendevc5.png");

        }
        userRepository.saveAndFlush(exuser);
    }
    @Override
    public void update2(int id, User user) {
        User exuser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        exuser.setFirstName(user.getFirstName());
        exuser.setLastName(user.getLastName());
       // exuser.setDoB(user.getDoB());
       // exuser.setAddress(user.getAddress());
        exuser.setProfilePicture(user.getProfilePicture());
        exuser.setBackGroundPicture(user.getBackGroundPicture());
        exuser.setEmail(user.getEmail());
        exuser.setPhone(user.getPhone());
        userRepository.saveAndFlush(exuser);
    }
    @Override
    public Boolean checkEmail(String email){
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(" Not Found"));
            return true;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }


    @Override
    public List<UserProgress> loadListTeacherProgressInGroup(int userid, int groupid){
        try {
            List<User> userList = userRepository.findDistinctUserRepliesNotCreatorsByGroupId(groupid);
            List<UserProgress> userProgressList =new ArrayList<>();
            for (User user: userList){
                UserProgress userProgress = new UserProgress();
                UserResponse profile = userMapper.toResponse(user);
                for (int i=0; i< user.getPostList().size(); i++){
                    profile.getPostList().get(i).setCreateBy(userMapper.toResponsePost(user.getPostList().get(i).getCreateBy()));
                    if (user.getPostList().get(i).getGroups() != null)
                    {profile.getPostList().get(i).setGroupid(user.getPostList().get(i).getGroups().getId());
                        profile.getPostList().get(i).setGroupname(user.getPostList().get(i).getGroups().getName());}
                    profile.getPostList().get(i).setStatus(user.getPostList().get(i).getStatus());
                    profile.getPostList().get(i).setSaveItemList(user.getPostList().get(i).getPageMemberships());

                }
                for (int i=0; i< user.getPostListReply().size(); i++){
                    profile.getPostListReply().get(i).setCreateBy(userMapper.toResponsePost(user.getPostListReply().get(i).getCreateBy()));
                    profile.getPostListReply().get(i).setGroupid(user.getPostListReply().get(i).getGroups().getId());
                    profile.getPostListReply().get(i).setGroupname(user.getPostListReply().get(i).getGroups().getName());
                    profile.getPostListReply().get(i).setStatus(user.getPostListReply().get(i).getStatus());
                    profile.getPostListReply().get(i).setSaveItemList(user.getPostListReply().get(i).getPageMemberships());

                }
                List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
                for(PostResponse itempost:profile.getPostList())
                {
                    PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                    itemPostResponseDTO.setId(itempost.getId());
                    itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                    itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                    itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                    itemPostResponseDTO.setSave_count(itempost.getSave_count());
                    itemPostResponseDTO.setContentPost(itempost.getContentPost());
                    itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                    for(LikeResponse itemlike:  itempost.getListLike())
                    {
                        if(itemlike.getCreateBy().getId()==userid)
                        {
                            itemPostResponseDTO.setUser_liked(true);

                        }
                        else {
                            itemPostResponseDTO.setUser_liked(false);
                        }
                    }
                    if (itempost.getSaveItemList() == null)
                    itemPostResponseDTO.setUser_saved(false);
                else

                    for (SaveItem itemlike : itempost.getSaveItemList()) {
                        if (itemlike.getPage().getId() == userid) {
                            itemPostResponseDTO.setUser_saved(true);
                            break;
                        }
                        itemPostResponseDTO.setUser_saved(false);
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());
                    itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                    itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                    itemPostResponseDTO.setGroupname(itempost.getGroupname());
                    postResponseDTOList.add(itemPostResponseDTO);
                }
                UserResponseDTO responseDTO=new UserResponseDTO();
                responseDTO.setProfilePicture(profile.getProfilePicture());
                responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
                responseDTO.setId(profile.getId());
                responseDTO.setEmail(profile.getEmail());
                responseDTO.setAddress(profile.getAddress());
                responseDTO.setFirstName(profile.getFirstName());
                responseDTO.setFriendships(profile.getFriendships());
                responseDTO.setCountFriend(profile.getCountFriend());
                responseDTO.setPostList(postResponseDTOList);
                responseDTO.setPhone(profile.getPhone());
                responseDTO.setLastName(profile.getLastName());
                responseDTO.setRole(profile.getRole());



                userProgress.setUserResponse(responseDTO);
                userProgress.setCountAllPostReply(profile.getPostListReply().size());
                int count = 0;
                if (profile.getPostListReply().size()!=0){
                    for (PostResponse postResponse : profile.getPostListReply()){
                        if (postResponse.getLisCmt().size()!=0){
                            for (Comments comments : postResponse.getLisCmt()){
                                if (comments.isAnwser() == true){
                                    count ++;
                                    break;
                                }
                            }
                        }
                    }
                }
                userProgress.setCountPostReplied(count);
                userProgressList.add(userProgress);
            }
            return userProgressList;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public List<UserProgress> loadListTeacherProgressInSector(int iduser,int groupid, int sectorid){
        try {
            List<User> userList = userRepository.findDistinctUserRepliesNotCreatorsByGroupIdAndSectorId( sectorid);
            List<UserProgress> userProgressList =new ArrayList<>();
            for (User user: userList){
                UserProgress userProgress = new UserProgress();
                UserResponse profile = userMapper.toResponse(user);
                for (int i=0; i< user.getPostList().size(); i++){
                    profile.getPostList().get(i).setCreateBy(userMapper.toResponsePost(user.getPostList().get(i).getCreateBy()));
                    if (user.getPostList().get(i).getGroups() != null)
                    {profile.getPostList().get(i).setGroupid(user.getPostList().get(i).getGroups().getId());
                        profile.getPostList().get(i).setGroupname(user.getPostList().get(i).getGroups().getName());}
                    profile.getPostList().get(i).setStatus(user.getPostList().get(i).getStatus());
                    profile.getPostList().get(i).setSaveItemList(user.getPostList().get(i).getPageMemberships());

                }
                for (int i=0; i< user.getPostListReply().size(); i++){
                    profile.getPostListReply().get(i).setCreateBy(userMapper.toResponsePost(user.getPostListReply().get(i).getCreateBy()));
                    profile.getPostListReply().get(i).setGroupid(user.getPostListReply().get(i).getGroups().getId());
                    profile.getPostListReply().get(i).setGroupname(user.getPostListReply().get(i).getGroups().getName());
                    profile.getPostListReply().get(i).setStatus(user.getPostListReply().get(i).getStatus());
                    profile.getPostListReply().get(i).setSaveItemList(user.getPostListReply().get(i).getPageMemberships());

                }
                List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
                for(PostResponse itempost:profile.getPostList())
                {
                    PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
                    itemPostResponseDTO.setId(itempost.getId());
                    itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                    itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                    itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                    itemPostResponseDTO.setSave_count(itempost.getSave_count());
                    itemPostResponseDTO.setContentPost(itempost.getContentPost());
                    itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                    for(LikeResponse itemlike:  itempost.getListLike())
                    {
                        if(itemlike.getCreateBy().getId()==iduser)
                        {
                            itemPostResponseDTO.setUser_liked(true);

                        }
                        else {
                            itemPostResponseDTO.setUser_liked(false);
                        }
                    }
                    if (itempost.getSaveItemList().size() == 0)
                        itemPostResponseDTO.setUser_saved(false);
                    else
                    for (SaveItem itemlike : itempost.getSaveItemList()) {
                        if (itemlike.getPage().getId() == iduser) {
                            itemPostResponseDTO.setUser_saved(true);
                            break;
                        }
                        itemPostResponseDTO.setUser_saved(false);
                    }

                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());
                    itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                    itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                    itemPostResponseDTO.setGroupname(itempost.getGroupname());
                    postResponseDTOList.add(itemPostResponseDTO);
                }
                UserResponseDTO responseDTO=new UserResponseDTO();
                responseDTO.setProfilePicture(profile.getProfilePicture());
                responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
                responseDTO.setId(profile.getId());
                responseDTO.setEmail(profile.getEmail());
                responseDTO.setAddress(profile.getAddress());
                responseDTO.setFirstName(profile.getFirstName());
                responseDTO.setFriendships(profile.getFriendships());
                responseDTO.setCountFriend(profile.getCountFriend());
                responseDTO.setPostList(postResponseDTOList);
                responseDTO.setPhone(profile.getPhone());
                responseDTO.setLastName(profile.getLastName());
                responseDTO.setRole(profile.getRole());



                userProgress.setUserResponse(responseDTO);
                userProgress.setCountAllPostReply(profile.getPostListReply().size());
                int count = 0;
                if (profile.getPostListReply().size()!=0){
                    for (PostResponse postResponse : profile.getPostListReply()){
                        if (postResponse.getLisCmt().size()!=0){
                            for (Comments comments : postResponse.getLisCmt()){
                                if (comments.isAnwser() == true){
                                    count ++;
                                    break;
                                }
                            }
                        }
                    }
                }
                userProgress.setCountPostReplied(count);
                userProgressList.add(userProgress);
            }
            return userProgressList;
        } catch (ApplicationException ex) {
            throw ex;
        }

    }
    @Override
    public UserProgress loadListTeacherProgress(int iduser){
        try {
            User user = userRepository.findById(iduser).orElseThrow(() -> new NotFoundException(" Not Found"));
                UserProgress userProgress = new UserProgress();
                UserResponse profile = userMapper.toResponse(user);
            for (int i=0; i< user.getPostList().size(); i++){
                profile.getPostList().get(i).setCreateBy(userMapper.toResponsePost(user.getPostList().get(i).getCreateBy()));
                if (user.getPostList().get(i).getGroups() != null)
                {profile.getPostList().get(i).setGroupid(user.getPostList().get(i).getGroups().getId());
                profile.getPostList().get(i).setGroupname(user.getPostList().get(i).getGroups().getName());}
                profile.getPostList().get(i).setStatus(user.getPostList().get(i).getStatus());
                profile.getPostList().get(i).setSaveItemList(user.getPostList().get(i).getPageMemberships());

            }
            for (int i=0; i< user.getPostListReply().size(); i++){
                profile.getPostListReply().get(i).setCreateBy(userMapper.toResponsePost(user.getPostListReply().get(i).getCreateBy()));
                profile.getPostListReply().get(i).setGroupid(user.getPostListReply().get(i).getGroups().getId());
                profile.getPostListReply().get(i).setGroupname(user.getPostListReply().get(i).getGroups().getName());
                profile.getPostListReply().get(i).setStatus(user.getPostListReply().get(i).getStatus());
                profile.getPostListReply().get(i).setSaveItemList(user.getPostListReply().get(i).getPageMemberships());

            }
                List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
                for(PostResponse itempost:profile.getPostList())
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
                        if(itemlike.getCreateBy().getId()==iduser)
                        {
                            itemPostResponseDTO.setUser_liked(true);

                        }
                        else {
                            itemPostResponseDTO.setUser_liked(false);
                        }
                    }
                    if (itempost.getSaveItemList() == null)
                        itemPostResponseDTO.setUser_saved(false);
                    else
                    for (SaveItem itemlike : itempost.getSaveItemList()) {
                        if (itemlike.getPage().getId() == iduser) {
                            itemPostResponseDTO.setUser_saved(true);
                            break;
                        }
                        itemPostResponseDTO.setUser_saved(false);
                    }
                    itemPostResponseDTO.setListAnh(itempost.getListAnh());
                    itemPostResponseDTO.setStatus(itempost.getStatus());
                    itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                    itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                    itemPostResponseDTO.setGroupname(itempost.getGroupname());
                    postResponseDTOList.add(itemPostResponseDTO);
                }
                UserResponseDTO responseDTO=new UserResponseDTO();
                responseDTO.setProfilePicture(profile.getProfilePicture());
                responseDTO.setBackGroundPicture(profile.getBackGroundPicture());
                responseDTO.setId(profile.getId());
                responseDTO.setEmail(profile.getEmail());
                responseDTO.setAddress(profile.getAddress());
                responseDTO.setFirstName(profile.getFirstName());
                responseDTO.setFriendships(profile.getFriendships());
                responseDTO.setCountFriend(profile.getCountFriend());
                responseDTO.setPostList(postResponseDTOList);
                responseDTO.setPhone(profile.getPhone());
                responseDTO.setLastName(profile.getLastName());
                responseDTO.setRole(profile.getRole());
                userProgress.setUserResponse(responseDTO);
                userProgress.setCountAllPostReply(profile.getPostListReply().size());
                int count = 0;
                if (profile.getPostListReply().size()!=0){
                    for (PostResponse postResponse : profile.getPostListReply()){
                        if (postResponse.getLisCmt().size()!=0){
                            for (Comments comments : postResponse.getLisCmt()){
                                if (comments.isAnwser() == true){
                                    count ++;
                                    break;
                                }
                            }
                        }
                    }
                }
                userProgress.setCountPostReplied(count);
            return userProgress;
        } catch (ApplicationException ex) {
            throw ex;
        }

    }
}
