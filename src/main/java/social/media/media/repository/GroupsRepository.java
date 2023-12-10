package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups,Integer> {

}
