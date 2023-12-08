package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;

import java.util.List;

@Repository
public interface friendsRepository extends JpaRepository<friends,Integer> {
   @Query("SELECT f FROM friends f WHERE (   f.user = :user OR f.friend = :friend) AND f.status = :status")
   List<friends> findByUserOrFriendAndStatus(@Param("user") User user,
                                             @Param("friend") User friend,
                                             @Param("status") Boolean status);
   @Query("SELECT f FROM friends f WHERE ( f.friend = :friend) AND f.status = :status")
   List<friends> findByUserOrFriendAndStatus(
                                             @Param("friend") User friend,
                                             @Param("status") Boolean status);
   public friends findByIdAndStatus (int id,Boolean Status);
}
