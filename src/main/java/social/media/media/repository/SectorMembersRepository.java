package social.media.media.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;

import java.util.List;

@Repository
public interface SectorMembersRepository extends JpaRepository<SectorMembers,Integer> {
    @Query("SELECT sm FROM SectorMembers sm " +
            "JOIN sm.sectors s " +
            "WHERE s.groupId.id = :groupId")
    List<SectorMembers> findAllByGroupId(@Param("groupId") int groupId);
    @Query("SELECT sm FROM SectorMembers sm " +
            "JOIN sm.sectors s " +
            "WHERE s.id = :groupId")
    List<SectorMembers> findAllBySectorId(@Param("groupId") int groupId);
}
