package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.User;
import social.media.media.model.entity.interations;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Integer> {

    @Query("SELECT c FROM Comments c WHERE c.CreateBy.id = :user ORDER BY c.timeStamp DESC")
    List<Comments> findAllByCreateBy(int user);

    @Query("SELECT c FROM Comments c " +
            "JOIN c.postID p " +
            "JOIN p.classes cl " +
            "WHERE cl.teacher.id = :adminId " +
            "ORDER BY c.timeStamp DESC")
    List<Comments> findAllCommentsByAdminId(@Param("adminId") int adminId, Pageable pageable);
    @Query("SELECT c FROM Comments c " +
            "WHERE c.CreateBy.id = :adminId " +
            "ORDER BY c.timeStamp DESC")
    List<Comments> findAllCommentsByMeInGroup(@Param("adminId") int adminId, Pageable pageable);
}
