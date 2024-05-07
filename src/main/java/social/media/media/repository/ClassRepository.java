package social.media.media.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.Groups;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classes,Integer> {

   // List<Groups> findByNameContaining(String key);

    @Query("SELECT COUNT(c) FROM Classes c WHERE c.teacher.id = :adminId")
    int countClassesByAdminId(@Param("adminId") int adminId);

    @Query("SELECT COUNT(p) FROM Post p JOIN p.classes c WHERE c.teacher.id = :adminId")
    int countPostsByAdminId(@Param("adminId") int adminId);

    @Query("SELECT COUNT(p) FROM Post p " +
            "JOIN p.classes c " +
            "LEFT JOIN p.lisCmt cmt " +
            "WHERE c.teacher.id = :adminId AND cmt IS NULL")
    int countPostsByAdminIdWithoutComments(@Param("adminId") int adminId);
}
