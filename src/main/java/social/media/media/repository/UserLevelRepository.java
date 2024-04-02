package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.User;
import social.media.media.model.entity.UserLevel;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLevelRepository extends JpaRepository<UserLevel,Integer> {
List<UserLevel> findAllByOrderByMaxPointsAsc();
}
