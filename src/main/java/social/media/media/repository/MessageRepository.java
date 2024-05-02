package social.media.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.MessageBox;

import java.util.List;
import java.util.Optional;import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface MessageRepository extends JpaRepository<MessageBox,Integer> {
            MessageBox findByClasses(Classes classes);

}
