package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.*;

@Repository
public interface GroupsMembersRepository extends JpaRepository<GroupMembers,Integer> {

    GroupMembers findByUserAndGroup(User user, Groups groups);
}
