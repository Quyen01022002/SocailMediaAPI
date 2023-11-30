package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;

@Repository
public interface PageMembersRepository extends JpaRepository<PageMembers,Integer> {

    PageMembers findByUserAndPage(User user, page page);
}
