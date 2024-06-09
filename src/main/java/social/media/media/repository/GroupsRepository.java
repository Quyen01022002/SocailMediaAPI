package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.User;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups,Integer> {

    List<Groups> findByNameContaining(String key);
    @Query("SELECT u FROM Groups u WHERE LOWER(u.name) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Groups> searchByName(@Param("keyword") String keyword);
}
