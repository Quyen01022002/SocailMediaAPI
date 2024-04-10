package social.media.media.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Save;
import social.media.media.model.entity.SaveItem;

@Repository
public interface SaveItemRepository extends JpaRepository<SaveItem,Integer> {
     SaveItem findByPageAndPost(Save page, Post post);
}
