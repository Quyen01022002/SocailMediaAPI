package social.media.media.service;

import social.media.media.model.entity.Report;
import social.media.media.model.reponse.*;

import java.util.List;

public interface ReportService {
    public ReportReponse reportUser(Report report);
    public void reportPost(Report report);
    public void Delete(int id);
    public PostResponse DuyetPost(int id);
    public List<ReportReponse> listReportPost(int report);
    public List<PostResponseDTO> listPosstWaiting(int groupid);

}
