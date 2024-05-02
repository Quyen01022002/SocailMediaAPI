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
import social.media.media.model.mapper.ClassMapper;
import social.media.media.model.mapper.GroupMapper;
import social.media.media.model.mapper.GroupsMembersMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.*;
import social.media.media.model.request.GroupAdminRequest;
import social.media.media.repository.*;
import social.media.media.service.ClassService;
import social.media.media.service.GroupService;
import social.media.media.service.MessageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final ClassMembersRepository classMembersRepository;
    private final SaveRepository pageRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    GroupsMembersMapper groupsMembersMapper;

    @Autowired
    PostMapper postMapper;


    @Override
    public ClassResponse addClasses(Classes groups) {
        try {

            Classes savedGroups=classRepository.saveAndFlush(groups);
            MessageBox messageBox=new MessageBox();
            messageBox.setAvatar(savedGroups.getAvatar());
            messageBox.setName(savedGroups.getName());
            messageService.createMessage(messageBox,savedGroups.getTeacher(),savedGroups);
            // Map to Response
            return classMapper.toResponse(savedGroups);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public ClassResponse updateClasses(Classes groups) {
        try {
            Classes exClasses = classRepository.findById(groups.getId()).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (exClasses == null) {
                throw new NotFoundException("Not Found");
            }


            exClasses.setDescription(groups.getDescription());
            exClasses.setName(groups.getName());
            // Update
            classRepository.saveAndFlush(exClasses);

            // Map to Response
            return classMapper.toResponse(exClasses);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<ClassResponse> ListGroupsAdmin(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));

            return  classMapper.toResponseList(user.getListTeacher());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void addMemberClass(ClassMembers groupsMembersResponse) {
        try {
            classMembersRepository.saveAndFlush(groupsMembersResponse);
            Classes classes=new Classes();
            classes.setId(groupsMembersResponse.getClasses().getId());
            MessageBox messageBox=messageRepository.findByClasses(classes);
            messageService.addMembers(messageBox,groupsMembersResponse.getUser());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void DeleteMembers(int page) {
        try {
            ClassMembers classMembers = classMembersRepository.findById(page).orElseThrow();
            if (classMembers == null) {
                throw new NotFoundException("Product Not Found");
            }


            classMembersRepository.delete(classMembers);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public ClassResponse Detail(int id) {
        try {
            Classes page = classRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            return classMapper.toResponse(page);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public void updateAvatar(int id, String Avatar) {


            Classes user = classRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            user.setAvatar(Avatar);
            classRepository.saveAndFlush(user);



    }
}
