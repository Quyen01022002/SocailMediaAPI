package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.InterationsMapper;
import social.media.media.model.mapper.NoticationsMapper;
import social.media.media.model.reponse.NoticationsResponse;
import social.media.media.model.reponse.UserResponse;
import social.media.media.repository.InerationsRepository;
import social.media.media.repository.NoticationsRepository;
import social.media.media.service.InterationService;
import social.media.media.service.NoticationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticationsServiceImpl implements NoticationService {
    @Autowired
    NoticationsRepository noticationsRepository;
    @Autowired
    NoticationsMapper noticationsMapper;


    @Override
    public NoticationsResponse addNotication(notications notications) {
        try {
            notications cmt2 = noticationsRepository.saveAndFlush(notications);
            return noticationsMapper.toResponse(cmt2);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<NoticationsResponse> listNotication(int id) {
        User user=new User();
        user.setId(id);
        List<notications> list = noticationsRepository.findByUser(user);
        List<NoticationsResponse> ListResponse=noticationsMapper.toListResponse(list);

        return ListResponse;
    }
    @Override
    public List<NoticationsResponse> listNotiUnRead(int id) {
        User user=new User();
        user.setId(id);
        List<notications> list = noticationsRepository.findByUserAndIsRead(user,false);
        List<NoticationsResponse> ListResponse=noticationsMapper.toListResponse(list);
        return ListResponse;
    }
    @Override
    public NoticationsResponse updateNotications(int id) {
        try {
            notications ExNotications = noticationsRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (ExNotications == null) {
               return null;
            }

            ExNotications.setIsRead(true);

            // Update
            noticationsRepository.saveAndFlush(ExNotications);

            // Map to Response
            return noticationsMapper.toResponse(ExNotications);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public Void Delete(notications post) {
        return null;
    }
}
