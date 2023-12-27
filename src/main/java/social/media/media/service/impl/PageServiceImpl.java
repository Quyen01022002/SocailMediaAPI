package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PageMapper;
import social.media.media.model.mapper.PageMembersMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.reponse.PageResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.UserResponse;
import social.media.media.model.request.PageAdminRequest;
import social.media.media.repository.PageMembersRepository;
import social.media.media.repository.PageRepository;
import social.media.media.repository.UserFollowRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.PageService;
import social.media.media.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PageMembersRepository pageMembersRepository;

    private final UserRepository userRepository;
    @Autowired
    PageMapper pageMapper;
    @Autowired
    PageMembersMapper pageMembersMapper;


    @Override
    public PageResponse addPage(page page) {
        try {

            page savedPage=pageRepository.saveAndFlush(page);
            if(page.getGroupMembers()!=null) {
                for (PageMembers item : page.getGroupMembers()) {
                    item.setId(savedPage.getId());
                    pageMembersRepository.saveAndFlush(item);
                }
            }

            // Map to Response
            return pageMapper.toResponse(savedPage);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public PageResponse Detail(int id) {
        try {
            page page = pageRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            return pageMapper.toResponse(page);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<PageMembersResponse> ListPage(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            return pageMembersMapper.toResponseList(user.getPageMemberships());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }


    @Override
    public PageResponse updatePage(page page) {
        page expage = pageRepository.findById(page.getId()).orElseThrow(() -> new NotFoundException(" Not Found"));
        page.setAdminId((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
       return pageMapper.toResponse(pageRepository.saveAndFlush(page));
    }
    @Override
    public List<PageResponse> getPageFollow(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            List<PageResponse> result = new ArrayList<>();
            for (PageMembers item: user.getPageMemberships()){
                result.add(pageMapper.toResponse(item.getPage()));
            }

            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public List<PageResponse> getPageAdmin(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            List<PageResponse> result = new ArrayList<>();
            for (page item: user.getListPageAdmin()){
                result.add(pageMapper.toResponse(item));
            }

            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public List<PageMembersResponse> getFollowPage(int id) {
        try {
            page page = pageRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
           List<PageMembersResponse> result = new ArrayList<>();
           for (PageMembers item : page.getGroupMembers()){
               result.add(pageMembersMapper.toResponse(item));
           }
            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public PageResponse updateAdminPage(PageAdminRequest page) {
        try {
            page expage = pageRepository.findById(page.getPageId()).orElseThrow(() -> new NotFoundException(" Not Found"));
            if (expage == null){
                throw  new NotFoundException("Not Found");

            }
            User user = userRepository.findById(page.getAdminId()).orElseThrow(() -> new NotFoundException(" Not Found"));;
            expage.setAdminId(user);
            return pageMapper.toResponse(pageRepository.saveAndFlush(expage));
        } catch (ApplicationException ex){
            throw  ex;
        }
    }

    @Override
    public void addMemberPage(PageMembers pageMembers) {
        try {
                    pageMembersRepository.saveAndFlush(pageMembers);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public page Delete(page page) {
        try {
            page page1 = pageRepository.findById(page.getId()).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (page1 == null) {
                throw new NotFoundException(" Not Found");
            }


            pageRepository.delete(page1);
            return page1;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void DeleteMembers(PageMembers page) {
        try {
            PageMembers pageMembers = pageMembersRepository.findByUserAndPage(page.getUser(),page.getPage());
            if (pageMembers == null) {
                throw new NotFoundException("Product Not Found");
            }


            pageMembersRepository.delete(pageMembers);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void updateAvatar(int id, String Avatar) {
        page user = pageRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setAvatar(Avatar);
        pageRepository.saveAndFlush(user);

    }

    @Override
    public void updateBack(int id, String Avatar) {
        page user = pageRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setBackAvatar(Avatar);
        pageRepository.saveAndFlush(user);

    }

    @Override
    public List<PageResponse> getAllPages() {
        try {
            List<page> allPages = pageRepository.findAll(); // Lấy tất cả các trang từ repository
            return pageMapper.toResponseList(allPages); // Chuyển đổi danh sách các trang thành danh sách PageResponse
        } catch (Exception ex) {
            throw new ApplicationException("Failed to retrieve all pages: " + ex.getMessage());
        }
    }

    @Override
    public PageMembersResponse getBoolFollow(int pageid, int userid){
        try {
            PageMembers pageMembers = pageMembersRepository.findByUserAndPage(
                    userRepository.findById(userid).orElseThrow(() -> new NotFoundException(" Not Found")),
                    pageRepository.findById(pageid).orElseThrow(() -> new NotFoundException(" Not Found"))
            );
            return pageMembersMapper.toResponse(pageMembers);
        } catch (Exception ex) {
            throw new ApplicationException("Failed to retrieve all pages: " + ex.getMessage());
        }
    }
}
