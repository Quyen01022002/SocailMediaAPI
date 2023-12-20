package social.media.media.service;

import social.media.media.model.entity.*;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.reponse.PageResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.request.PageAdminRequest;

import java.util.List;

public interface PageService {
    public PageResponse addPage(page page);
    public PageResponse Detail(int id);
    public List<PageMembersResponse> ListPage(int id);
    public PageResponse updatePage(page page);
    public List<PageResponse> getPageFollow(int id);
    public List<PageResponse> getPageAdmin(int id);
    public List<PageMembersResponse> getFollowPage(int pageid);
    public PageResponse updateAdminPage(PageAdminRequest page);
    public void addMemberPage(PageMembers pageMembers);
    public page Delete(page page);
    public void DeleteMembers(PageMembers page);

    public List<PageResponse> getAllPages();

    public PageMembersResponse getBoolFollow(int pageid, int userid);

}
