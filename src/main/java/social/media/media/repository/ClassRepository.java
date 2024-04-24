package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.Groups;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classes,Integer> {

   // List<Groups> findByNameContaining(String key);
}
