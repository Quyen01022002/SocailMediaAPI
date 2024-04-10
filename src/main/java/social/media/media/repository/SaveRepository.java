package social.media.media.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import social.media.media.model.entity.Save;
import social.media.media.model.entity.User;

@Repository
public interface SaveRepository extends JpaRepository<Save,Integer> {
    Save findByAdminId(User user);
}
