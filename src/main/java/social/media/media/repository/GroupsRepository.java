package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Groups;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups,Integer> {

    List<Groups> findByNameContaining(String key);
}
