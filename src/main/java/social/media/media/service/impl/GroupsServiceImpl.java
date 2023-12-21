package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.GroupMapper;
import social.media.media.model.mapper.GroupsMembersMapper;
import social.media.media.model.mapper.PageMapper;
import social.media.media.model.mapper.PageMembersMapper;
import social.media.media.model.reponse.GroupsMembersResponse;
import social.media.media.model.reponse.GroupsResponse;
import social.media.media.model.reponse.PageMembersResponse;
import social.media.media.model.reponse.PageResponse;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.repository.*;
import social.media.media.service.GroupService;
import social.media.media.service.PageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupService {

    private final GroupsRepository groupsRepository;
    private final GroupsMembersRepository groupsMembersRepository;
    private final PageRepository pageRepository;
    private final UserRepository userRepository;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    GroupsMembersMapper groupsMembersMapper;




    @Override
    public GroupsResponse addPage(Groups groups) {
        try {

            Groups savedGroups=groupsRepository.saveAndFlush(groups);
            if(groups.getGroupMembers()!=null) {
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
            User user = userRepository.findById(groups.getAdminId()).orElseThrow(() -> new NotFoundException(" Not Found"));;
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
            List<GroupsResponse> result=new ArrayList<>();
            for(GroupMembers item:user.getGroupMemberships()) {
                if (item.getGroup().getAdminId().getId() != user.getId())
                result.add(groupMapper.toResponse(item.getGroup()));
            }
            return result;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<GroupsResponse> ListGroupsAdmin(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));

            return  groupMapper.toResponseList(user.getListGroupAdmin());
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
            GroupMembers pageMembers = groupsMembersRepository.findByUserAndGroup(page.getUser(),page.getGroup());
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


}
