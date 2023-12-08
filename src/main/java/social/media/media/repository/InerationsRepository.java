package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.User;
import social.media.media.model.entity.interations;

import java.util.List;

@Repository
public interface InerationsRepository extends JpaRepository<interations,Integer> {
    interations findByCreateByAndPostID(User createBy, Post postID);

}
