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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final ClassMembersRepository classMembersRepository;
    private final SaveRepository pageRepository;
    private final UserRepository userRepository;
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


            // Map to Response
            return classMapper.toResponse(savedGroups);
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
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
}
