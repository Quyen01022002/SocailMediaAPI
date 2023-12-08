package social.media.media.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import social.media.media.model.entity.User;

import java.util.List;
import java.util.Optional;import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);
    @Query(value = "SELECT u FROM User u ORDER BY RAND()")
    List<User> findRandom20Users();
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(u.phone) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(concat('%', :keyword, '%'))")
    List<User> searchByNameOrLastNameOrPhoneOrEmail(@Param("keyword") String keyword);



}
