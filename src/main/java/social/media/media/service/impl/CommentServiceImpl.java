package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.interations;
import social.media.media.model.mapper.CommentMapper;
import social.media.media.model.mapper.InterationsMapper;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.repository.CommentRepository;
import social.media.media.repository.InerationsRepository;
import social.media.media.service.CommentService;
import social.media.media.service.InterationService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;


    @Override
    public CommentsResponse Comments(Comments cmt) {
        try {
            Comments cmt2 = commentRepository.saveAndFlush(cmt);
            return commentMapper.toResponse(cmt2);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public CommentsResponse Update(Comments cmt) {
        Comments expage = commentRepository.findById(cmt.getCommentId()).orElseThrow(() -> new NotFoundException(" Not Found"));

        return commentMapper.toResponse(commentRepository.saveAndFlush(cmt));
    }

    @Override
    public void Delete(int id) {
        try {
            Comments page1 = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (page1 == null) {
                throw new NotFoundException(" Not Found");
            }


            commentRepository.delete(page1);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
}
