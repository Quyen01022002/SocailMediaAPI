package social.media.media.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Report;
import social.media.media.model.entity.SavedPost;
import social.media.media.model.entity.User;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

    @Query("SELECT r FROM Report r WHERE r.reporterID.id = :userId AND r.reportedPostID.id = :postId")
    List<Report> findByUserIdAndPostId(@Param("userId") int userId, @Param("postId") int postId);
    @Query("SELECT r FROM Report r WHERE r.reporterID.id = :userId AND r.reportedUserID.id = : userId2")
    List<Report> findByUserIdAndUserId(@Param("userId") int userId, @Param("userId") int userId2);

    @Query("SELECT r FROM Report r " +
            "JOIN r.reportedPostID p " +
            "JOIN p.groups g " +
            "WHERE g.id = :groupId AND r.status = false ORDER BY r.timestamp DESC")
    List<Report> findReportsByGroupIdAndStatusFalse(@Param("groupId") int groupId, Pageable pageable);

}
