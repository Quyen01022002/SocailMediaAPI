package social.media.media.service;

import social.media.media.model.entity.*;
import social.media.media.model.reponse.FriendsResponse;
import social.media.media.model.reponse.IntactionResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;

import java.util.List;
import java.util.Map;

public interface PostService {
    public PostResponse addPost(Post post, List<pictureOfPost> listImg);
    public PostResponse updatePost(Post post, List<pictureOfPost> listImg);
    public PostResponse findOne(int postId);

    public Void Delete(int id);

    public List<PostResponse> getTop10();
    public List<PostResponse> getTop10Teacher(int adminId);
    public List<PostResponse> searchPost(String keyword, int pagenumber);
    public List<IntactionResponse> getAllMyLike(int userid);
    public List<PostResponse> getPostClass(int userid, int pagenumber);
    public List<PostResponse> getMyPost(int userid, int pagenumber);
    public List<PostResponse> getOtherPost(int userid, int pagenumber);
    public List<PostResponse> getTop5OnMonth(int userid);
    public String getPostCountStringByMonthAndAdminId(int adminId);


    public List<PostResponseDTO> getPostOfAllGroupFollow(int iduser, int pagenumber);
    public List<PostResponseDTO> getPostReply(int iduser, int pagenumber);
    public List<PostResponseDTO> getPostNotReply(int teacherid);

    public PostResponse notSectorMe(int postid);
}
