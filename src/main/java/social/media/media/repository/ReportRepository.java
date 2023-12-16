package social.media.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.media.media.model.entity.Report;
import social.media.media.model.entity.SavedPost;
import social.media.media.model.entity.User;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {




}
