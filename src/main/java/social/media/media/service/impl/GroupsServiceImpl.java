package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.GroupMapper;
import social.media.media.model.mapper.GroupsMembersMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.repository.*;
import social.media.media.service.GroupService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupService {

    private final GroupsRepository groupsRepository;
    private final PostRepository postRepository;
    private final GroupsMembersRepository groupsMembersRepository;
    private final SaveRepository pageRepository;
    private final UserRepository userRepository;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    GroupsMembersMapper groupsMembersMapper;

    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public GroupsResponse addPage(Groups groups) {
        try {

            Groups savedGroups = groupsRepository.saveAndFlush(groups);
            if (groups.getGroupMembers() != null) {
                for (GroupMembers item : groups.getGroupMembers()) {
                    item.setId(savedGroups.getId());
                    groupsMembersRepository.saveAndFlush(item);
                }
            }

            // Map to Response
            return groupMapper.toResponse(savedGroups);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public GroupsResponse updateGroups(Groups groups) {
        try {
            Groups exGroups = groupsRepository.findById(groups.getId()).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (exGroups == null) {
                throw new NotFoundException("Not Found");
            }


            exGroups.setDescription(groups.getDescription());
            exGroups.setName(groups.getName());
            // Update
            groupsRepository.saveAndFlush(exGroups);

            // Map to Response
            return groupMapper.toResponse(exGroups);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public GroupsResponse updateAdminGroups(GroupAdminRequest groups) {
        try {
            Groups exGroups = groupsRepository.findById(groups.getGroupId()).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (exGroups == null) {
                throw new NotFoundException("Not Found");
            }
            User user = userRepository.findById(groups.getAdminId()).orElseThrow(() -> new NotFoundException(" Not Found"));
            ;
            exGroups.setAdminId(user);
            // Update
            groupsRepository.saveAndFlush(exGroups);

            // Map to Response
            return groupMapper.toResponse(exGroups);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public GroupsResponse Detail(int id) {
        try {
            Groups page = groupsRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            return groupMapper.toResponse(page);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<GroupsResponse> ListGroups(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            List<GroupsResponse> result = new ArrayList<>();
            for (GroupMembers item : user.getGroupMemberships()) {
                if (item.getGroup().getAdminId().getId() != user.getId()) {
                    GroupsResponse groupsResponse = new GroupsResponse();
                    groupsResponse = groupMapper.toResponse(item.getGroup());
                    Groups groups = groupsRepository.findById(item.getGroup().getId()).orElseThrow(() -> new NotFoundException(" Not Found"));
                    List<PostResponse> responseList = postMapper.toResponseList(groups.getListPost());
                    for (int i=0; i< responseList.size(); i++){
                        responseList.get(i).setCreateBy(userMapper.toResponsePost(groups.getListPost().get(i).getCreateBy()));
                    }
                    List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
                    for (PostResponse itempost : responseList) {
                        if(itempost.getStatus()) {
                            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
                            itemPostResponseDTO.setId(itempost.getId());
                            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
                            itemPostResponseDTO.setSave_count(itempost.getSave_count());
                            itemPostResponseDTO.setContentPost(itempost.getContentPost());
                            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
                            itemPostResponseDTO.setGroupid(itempost.getGroupid());
                            for (LikeResponse itemlike : itempost.getListLike()) {
                                if (itemlike.getCreateBy().getId() == id) {
                                    itemPostResponseDTO.setUser_liked(true);
                                    break;
                                }
                                itemPostResponseDTO.setUser_liked(false);
                            }
                            if (itempost.getSaveItemList().size() == 0)
                                itemPostResponseDTO.setUser_saved(false);
                            else
                            for (SaveItem itemlike : itempost.getSaveItemList()) {
                                if (itemlike.getPage().getId() == id) {
                                    itemPostResponseDTO.setUser_saved(true);
                                    break;
                                }
                                itemPostResponseDTO.setUser_saved(false);
                            }
                            itemPostResponseDTO.setListAnh(itempost.getListAnh());
                            itemPostResponseDTO.setStatus(itempost.getStatus());
                            itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                            itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                            itemPostResponseDTO.setGroupname(itempost.getGroupname());
                            postResponseDTOList.add(itemPostResponseDTO);
                        }
                    }

                    groupsResponse.setListPost(postResponseDTOList);


                    result.add(groupsResponse);
                }
            }
            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public Page<GroupsResponse> listGroups(int id, Pageable pageable) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
            List<GroupsResponse> result = new ArrayList<>();
            for (GroupMembers item : user.getGroupMemberships()) {
                if (item.getGroup().getAdminId().getId() != user.getId()) {
                    result.add(groupMapper.toResponse(item.getGroup()));
                }
            }
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<GroupsResponse> pageList;

            if (result.size() < startItem) {
                pageList = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, result.size());
                pageList = result.subList(startItem, toIndex);
            }

            return new PageImpl<>(pageList, PageRequest.of(currentPage, pageSize), result.size());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<GroupsResponse> ListGroupsAll() {
        try {
            List<GroupsResponse> result = groupMapper.toResponseList(groupsRepository.findAll());

            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<GroupsResponse> searchGroup(String keyword) {
        try {
            List<GroupsResponse> result = groupMapper.toResponseList(groupsRepository.findByNameContaining(keyword));

            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<GroupsResponse> ListGroupsAdmin(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));

            return groupMapper.toResponseList(user.getListGroupAdmin());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public GroupsResponse updatePage(Groups groups) {
        Groups expage = groupsRepository.findById(groups.getId()).orElseThrow(() -> new NotFoundException(" Not Found"));

        return groupMapper.toResponse(groupsRepository.saveAndFlush(groups));
    }

    @Override
    public void addMemberPage(GroupMembers groupsMembersResponse) {
        try {
            groupsMembersRepository.saveAndFlush(groupsMembersResponse);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }


    @Override
    public void Delete(int id) {
        try {
            Groups page1 = groupsRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (page1 == null) {
                throw new NotFoundException(" Not Found");
            }
            groupsRepository.delete(page1);

        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void DeleteMembers(GroupMembers page) {
        try {
            GroupMembers pageMembers = groupsMembersRepository.findByUserAndGroup(page.getUser(), page.getGroup());
            if (pageMembers == null) {
                throw new NotFoundException("Product Not Found");
            }


            groupsMembersRepository.delete(pageMembers);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void updateAvatar(int id, String Avatar) {

        Groups user = groupsRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
        user.setAvatar(Avatar);
        groupsRepository.saveAndFlush(user);


    }

    @Override
    public List<GroupsResponse> Search(String keyword) {

        List<Groups> list = groupsRepository.searchByName(keyword);
        return groupMapper.toResponseList(list);
    }

    @Override
    public List<PostResponse> loadPost(int groupid, int page, int userid) {
        PageRequest pageable = PageRequest.of(page, 6);
        List<Post> result = postRepository.findAllByGroupIdOrderByTimestampDesc(groupid, pageable);
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }
    @Override
    public List<PostResponse> loadPostOfAllGroupFollow(int pagenumber, int userid) {
        PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> result = postRepository.findPostsByUserGroups(userid, pageable);
        System.out.println(result.get(0).getStatus());
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }
    @Override
    public List<PostResponse> loadHotPostOfAllGroupFollow(int pagenumber, int userid) {
        PageRequest pageable = PageRequest.of(pagenumber, 6);
        List<Post> result = postRepository.findHotPostsByUserGroups(userid, pageable);
        System.out.println(result.get(0).getStatus());
        List<PostResponse> listPost = postMapper.toResponseList(result);
        for (int i=0; i< result.size(); i++){
            listPost.get(i).setCreateBy(userMapper.toResponsePost(result.get(i).getCreateBy()));
        }
        return listPost;
    }



}
