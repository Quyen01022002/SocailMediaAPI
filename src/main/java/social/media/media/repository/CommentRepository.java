package social.media.media.repository;


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
}
