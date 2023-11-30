package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

}
