package social.media.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.MessageMembers;
import social.media.media.model.entity.User;

import java.util.List;

@Repository
public interface MessageMembersRepository extends JpaRepository<MessageMembers,Integer> {

    List<MessageMembers> findByUser(User user);
}
