package social.media.media.service;

import social.media.media.model.entity.*;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.SaveResponse;

import java.util.List;

public interface PageService {
   public SaveResponse addPage(Save page);
    public List<PostResponse> ListPage(int id);
    public Save findSave(int userId);

    public void addMemberPage(SaveItem pageMembers);
    public void Delete(int save,int post);

}
