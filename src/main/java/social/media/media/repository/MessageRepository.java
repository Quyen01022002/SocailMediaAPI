package social.media.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.MessageBox;

import java.util.List;
import java.util.Optional;import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface MessageRepository extends JpaRepository<MessageBox,Integer> {

    List<MessageBox> findByUserIdAndFriendId(int userId, int friendId);
    @Query("SELECT m1 FROM MessageBox m1 " +
            "WHERE m1.id IN (" +
            "  SELECT MAX(m2.id) FROM MessageBox m2 " +
            "  WHERE m2.userId = :userId OR m2.friendId = :userId " +
            "  GROUP BY CASE WHEN m2.userId = :userId THEN m2.friendId ELSE m2.userId END)")
    List<MessageBox> loadListNewMessUser(@Param("id") int userId);

}
