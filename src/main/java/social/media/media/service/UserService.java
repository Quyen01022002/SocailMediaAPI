package social.media.media.service;

import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.userFollow;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserProgress;
import social.media.media.model.reponse.UserResponse;
import social.media.media.model.request.ResetPasswordRequest;

import java.util.List;

public interface UserService {
    public UserResponse profile(int id);
    public UserResponse profileByEmail(String email);
    public UserResponse profileByEmailAndPw(ResetPasswordRequest resetPasswordRequest);
    public void Follow(User user);
    public List<UserResponse> List20Follow();
    public List<UserResponse> Search(String key);
    public void unFollow(userFollow user);
    public void updateAvatar(int id,String Avatar);
    public void updatePoint(int id,int point);
    public void updateBackGround(int id,String Avatar);
    public void fcm(int id,String fcm);
    public void update(int id, User user);
    public void update2(int id, User user);
    public List<UserProgress> loadListTeacherProgressInGroup(int iduser, int groupid);
    public List<UserProgress> loadListTeacherProgressInSector(int iduser, int groupid, int sector);

    public Boolean checkEmail(String email);
}
