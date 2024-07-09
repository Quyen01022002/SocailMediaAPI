package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Report;
import social.media.media.model.entity.User;
import social.media.media.model.enums.StatusViewPostEnum;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT DISTINCT r FROM Report r WHERE r.reportedPostID.groups = :group")
    List<Report> findReportedPostsByGroup(@Param("group") Groups group);
    List<Post> findByGroupsAndStatus(Groups group,Boolean Status);

    @Query("SELECT p, COUNT(l) as likeCount FROM Post p LEFT JOIN p.listLike l " +
            "WHERE p.statusViewPostEnum = 'ALLUSER' " +
            "GROUP BY p.id, p.CreateBy, p.contentPost, p.timeStamp, p.status, p.classes, p.groups " +
            "ORDER BY likeCount DESC")
    List<Post> findTop10PostsByLikes(Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.contentPost LIKE %:key% " +
            "AND p.classes is null " +
            "AND p.status = true " +
            "AND p.statusViewPostEnum <> :statusViewPostEnum")
    List<Post> findByContentPostContaining(@Param("key") String key, @Param("statusViewPostEnum") StatusViewPostEnum statusViewPostEnum, Pageable pageable);


    @Query("SELECT p FROM Post p " +
            "JOIN p.classes c " +
            "WHERE c.teacher.id = :adminId " +
            "ORDER BY SIZE(p.listLike) DESC")
    List<Post> findTop10ByTeacherIdOrderByLikesDesc(@Param("adminId") int adminId, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "JOIN p.classes c " +
            "JOIN c.classMembers cm " +
            "WHERE cm.user.id = :userId " +
            "ORDER BY p.timeStamp DESC")
    List<Post> findPostsByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.CreateBy.id = :userId " +
            "ORDER BY p.timeStamp DESC")
    List<Post> findPostsByCreateById(@Param("userId") int userId, Pageable pageable);
    @Query("SELECT p FROM Post p " +
            "WHERE p.CreateBy.id = :userId " +
            "AND p.statusViewPostEnum <> 'ONLYME'"+
            "ORDER BY p.timeStamp DESC")
    List<Post> findPostsByCreateByIdOther(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "JOIN p.classes c " +
            "WHERE c.teacher.id = :adminId " +
            "AND MONTH(p.timeStamp) = MONTH(CURRENT_DATE) " + // Lọc theo tháng hiện tại
            "AND YEAR(p.timeStamp) = YEAR(CURRENT_DATE) " +   // Lọc theo năm hiện tại
            "ORDER BY SIZE(p.listLike) DESC")
    List<Post> findTop5PostsByAdminIdAndCurrentMonth(@Param("adminId") int adminId, Pageable pageable);

    @Query("SELECT MONTH(p.timeStamp) AS month, COUNT(p) AS postCount " +
            "FROM Post p " +
            "JOIN p.classes c " +
            "WHERE c.teacher.id = :adminId " +
            "AND YEAR(p.timeStamp) = YEAR(CURRENT_DATE) " +   // Lọc theo năm hiện tại
            "GROUP BY MONTH(p.timeStamp)")
    List<Map<String, Object>> findPostCountByMonthAndAdminId(@Param("adminId") int adminId);


    @Query("SELECT p FROM Post p WHERE p.groups.id = :groupId ORDER BY p.timeStamp DESC")
    List<Post> findAllByGroupIdOrderByTimestampDesc(@Param("groupId") int groupId, Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.groups.id IN (SELECT gm.group.id FROM GroupMembers gm WHERE gm.user.id = :userId) AND p.statusViewPostEnum <> :statusViewPostEnum ORDER BY p.timeStamp DESC")
    List<Post> findAllPostsFromFollowedGroups(int userId, StatusViewPostEnum statusViewPostEnum, Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.hotinday is null and p.status=true and p.groups.id IN (" +
            "SELECT gm.group.id FROM GroupMembers gm WHERE gm.user.id = :userId) " +
            "ORDER BY p.timeStamp DESC")
    List<Post> findPostsByUserGroups(@Param("userId") int userId, Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.hotinday is not null" +
            " and p.status=true and p.groups.id IN (" +
            "SELECT gm.group.id FROM GroupMembers gm WHERE gm.user.id = :userId) " +
            "ORDER BY p.timeStamp DESC")
    List<Post> findHotPostsByUserGroups(@Param("userId") int userId, Pageable pageable);


    List<Post> findAllByUserReplyId(int userId, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p " +
            "WHERE p.UserReply.id = :userReplyId " +
            "AND NOT EXISTS (SELECT c FROM Comments c WHERE c.postID.id = p.id AND c.isAnwser = true)")
    List<Post> findPostsByUserReplyWithUnansweredComments(@Param("userReplyId") int userReplyId);

    @Query("SELECT p FROM Post p JOIN Report r ON p.id = r.reportedPostID.id WHERE p.groups.id = :groupId")
    List<Post> findReportedPostsInGroup(int groupId, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.groups.id = :groupId AND p.hotinday is null" +
            " AND p.status = true" +
            " AND p.UserReply is null ORDER BY p.timeStamp DESC")
    List<Post> findAllByNotUserReplyId(@Param("groupId")int groupId, Pageable pageable);
}
