package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.PageMembers;
import social.media.media.model.entity.User;
import social.media.media.model.entity.page;
import social.media.media.model.entity.userFollow;

import java.util.Optional;

@Repository
public interface UserFollowRepository extends JpaRepository<userFollow,Integer> {
    userFollow findByFollowerAndFollowing (User follower, User following);
}
