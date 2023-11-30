package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;

import java.util.List;

@Repository
public interface friendsRepository extends JpaRepository<friends,Integer> {
   public List<friends> findByUserOrFriendAndStatus(User user, User friend,Boolean Status);
   public friends findByIdAndStatus (int id,Boolean Status);
}
