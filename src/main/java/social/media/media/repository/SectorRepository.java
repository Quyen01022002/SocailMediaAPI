package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Sector;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector,Integer> {

    List<Sector> findByGroupId(Groups groupId);



}
