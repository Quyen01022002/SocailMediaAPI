package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.ClassMembers;
import social.media.media.model.entity.Classes;

@Repository
public interface ClassMembersRepository extends JpaRepository<ClassMembers,Integer> {

   // List<Groups> findByNameContaining(String key);
}
