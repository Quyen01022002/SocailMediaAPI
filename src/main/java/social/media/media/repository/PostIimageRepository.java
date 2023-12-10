package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.pictureOfPost;

import java.util.List;

@Repository
public interface PostIimageRepository extends JpaRepository<pictureOfPost,Integer> {


    List<pictureOfPost> findByListAnh(Post post);

}
