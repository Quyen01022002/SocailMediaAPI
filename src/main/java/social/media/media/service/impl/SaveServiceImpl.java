package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PageMapper;
import social.media.media.model.mapper.PageMembersMapper;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.SaveResponse;
import social.media.media.repository.SaveItemRepository;
import social.media.media.repository.SaveRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.PageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveServiceImpl implements PageService {

    private final SaveRepository pageRepository;
    private final SaveItemRepository pageMembersRepository;

    private final UserRepository userRepository;
    @Autowired
    PageMapper pageMapper;
    @Autowired
    PageMembersMapper pageMembersMapper;


    @Override
    public SaveResponse addPage(Save page) {
        try {

            Save savedPage = pageRepository.saveAndFlush(page);

            // Map to Response
            return pageMapper.toResponse(savedPage);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<PostResponse> ListPage(int id) {
        return null;
    }

    @Override
    public Save findSave(int userId) {
        User user=new User();
        user.setId(userId);
        return pageRepository.findByAdminId(user);

    }

    @Override
    public void addMemberPage(SaveItem pageMembers) {
        try {
            pageMembersRepository.saveAndFlush(pageMembers);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void Delete(int save, int post) {
        try {
            Save sv=new Save();
            User user=new User();
            user.setId(save);
            sv=pageRepository.findByAdminId(user);
            Post p=new Post();
            p.setId(post);
            SaveItem page1 = pageMembersRepository.findByPageAndPost(sv,p);
            if (page1 == null) {
                throw new NotFoundException(" Not Found");
            }
            pageMembersRepository.delete(page1);

        } catch (ApplicationException ex) {
            throw ex;
        }
    }


//    @Override
//    public void addMemberPage(SaveItem pageMembers) {
//        try {
//                    pageMembersRepository.saveAndFlush(pageMembers);
//        } catch (ApplicationException ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public Save Delete(Save page) {
//        try {
//            Save page1 = pageRepository.findById(page.getId()).orElseThrow(() -> new NotFoundException("friend Not Found"));
//            if (page1 == null) {
//                throw new NotFoundException(" Not Found");
//            }
//
//
//            pageRepository.delete(page1);
//            return page1;
//        } catch (ApplicationException ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public void DeleteMembers(SaveItem page) {
//
//    }
//
//    @Override
//    public void updateAvatar(int id, String Avatar) {
//
//    }
//
//    @Override
//    public void updateBack(int id, String Avatar) {
//
//    }
//
//
//    @Override
//    public List<SaveResponse> getAllPages() {
//        try {
//            List<Save> allPages = pageRepository.findAll(); // Lấy tất cả các trang từ repository
//            return pageMapper.toResponseList(allPages); // Chuyển đổi danh sách các trang thành danh sách SaveResponse
//        } catch (Exception ex) {
//            throw new ApplicationException("Failed to retrieve all pages: " + ex.getMessage());
//        }
//    }
//
//    @Override
//    public PageMembersResponse getBoolFollow(int pageid, int userid){
//        try {
//            SaveItem pageMembers = pageMembersRepository.findByUserAndPage(
//                    userRepository.findById(userid).orElseThrow(() -> new NotFoundException(" Not Found")),
//                    pageRepository.findById(pageid).orElseThrow(() -> new NotFoundException(" Not Found"))
//            );
//            return pageMembersMapper.toResponse(pageMembers);
//        } catch (Exception ex) {
//            throw new ApplicationException("Failed to retrieve all pages: " + ex.getMessage());
//        }
//    }
}
