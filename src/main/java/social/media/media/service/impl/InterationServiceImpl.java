package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.interations;
import social.media.media.model.mapper.InterationsMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.repository.InerationsRepository;
import social.media.media.repository.PostRepository;
import social.media.media.service.InterationService;
import social.media.media.service.PostService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterationServiceImpl implements InterationService {

    private final InerationsRepository interInerationsRepository;
    @Autowired
    InterationsMapper interationsMapper;

    @Override
    public LikeResponse Like(interations like) {
        try {
            interations liked = interInerationsRepository.findById(like.getInteractionId()).orElseThrow(() -> new NotFoundException("Like Not Found"));

            if (liked == null) {
                interInerationsRepository.saveAndFlush(like);
            } else {
                interInerationsRepository.delete(like);
            }
            // Map to Response
            return interationsMapper.toResponse(like);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
}
