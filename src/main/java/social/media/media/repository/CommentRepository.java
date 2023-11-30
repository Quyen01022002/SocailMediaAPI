package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Comments;
import social.media.media.model.entity.interations;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Integer> {

}
