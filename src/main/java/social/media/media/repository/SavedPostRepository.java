package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.User;
import social.media.media.model.entity.SavedPost;

import java.util.List;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost,Integer> {

List<SavedPost> findByUser(User CreateBy);


}
