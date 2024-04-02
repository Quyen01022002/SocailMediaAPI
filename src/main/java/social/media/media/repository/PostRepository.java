package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Report;
import social.media.media.model.entity.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT DISTINCT r FROM Report r WHERE r.reportedPostID.groups = :group")
    List<Report> findReportedPostsByGroup(@Param("group") Groups group);
    List<Post> findByGroupsAndStatus(Groups group,Boolean Status);

        @Query("SELECT p, COUNT(l) as likeCount FROM Post p LEFT JOIN p.listLike l GROUP BY p.id, p.CreateBy, p.contentPost, p.timeStamp, p.status, p.page, p.groups ORDER BY likeCount DESC")
    List<Post> findTop10PostsByLikes(Pageable pageable);

        List<Post> findByContentPostContaining(String key);
}
