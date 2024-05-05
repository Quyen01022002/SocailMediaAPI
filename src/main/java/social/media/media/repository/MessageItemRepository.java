package social.media.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.Message;
import social.media.media.model.entity.MessageBox;

@Repository
public interface MessageItemRepository extends JpaRepository<Message,Integer> {

}
