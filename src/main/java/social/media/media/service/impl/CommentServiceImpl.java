package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.notications;
import social.media.media.model.mapper.CommentMapper;
import social.media.media.model.reponse.CommentsResponse;
import social.media.media.model.request.CommentRequest;
import social.media.media.repository.CommentRepository;
import social.media.media.repository.PostRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.CommentService;
import social.media.media.service.NoticationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    NoticationService noticationService;
    private final CommentRepository commentRepository;
    private  final UserRepository userRepository;
    @Autowired
    CommentMapper commentMapper;
    private final PostRepository postRepository;

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
    public CommentsResponse Comments2(CommentRequest cmt) {
        try {
            Comments cmt1 = commentMapper.toComment(cmt);
            Comments cmt2 = commentRepository.saveAndFlush(cmt1);
            notications notications=new notications();
            notications.setContentNotications("Bài viết của bạn vừa nhận một bình luận mới" );
            User user=new User();
            user.setId(cmt.getUser_id());
            notications.setUser(user);
            noticationService.addNotication(notications);
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
@Override
    public List<CommentsResponse> getAllComment(int id) {
        try {
            Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            return commentMapper.toListCommentResponse(post.getLisCmt());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public List<CommentsResponse> getAllMyComment(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            List<Comments> listcmt = commentRepository.findAllByCreateBy(id);
            return commentMapper.toListCommentResponse(listcmt);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public List<CommentsResponse> getAllMyCommentClasses(int id, int pagenumber) {
        try {
            PageRequest pageable = PageRequest.of(pagenumber, 6);
            List<Comments> listcmt = commentRepository.findAllCommentsByAdminId(id, pageable);
            return commentMapper.toListCommentResponse(listcmt);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Override
    public CommentsResponse setAnswer(int cmtid){
        try {
            Comments comments= commentRepository.findById(cmtid).orElseThrow(() -> new NotFoundException(" Not Found"));
            comments.setIsAnwser(true);
            Comments cmt2 = commentRepository.saveAndFlush(comments);
            return commentMapper.toResponse(cmt2);
        } catch (ApplicationException ex) {
            throw ex;
        }


    }
    @Override
    public CommentsResponse setAnswerToCmt(int cmtid){
        try {
            Comments comments= commentRepository.findById(cmtid).orElseThrow(() -> new NotFoundException(" Not Found"));
            comments.setIsAnwser(false);
            Comments cmt2 = commentRepository.saveAndFlush(comments);
            return commentMapper.toResponse(cmt2);
        } catch (ApplicationException ex) {
            throw ex;
        }


    }

}
