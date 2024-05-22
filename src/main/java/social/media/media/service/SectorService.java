package social.media.media.service;

import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;
import social.media.media.model.reponse.SectorMemberResponse;
import social.media.media.model.reponse.SectorResponse;
import social.media.media.model.request.SectorMemberRequest;

import java.util.List;

public interface SectorService {
 public SectorResponse addSector(Sector sector);
 public SectorMembers addTeacherSector(SectorMemberRequest sectorMemberRequest);


 public List<SectorResponse> getSectorInGroup(int groupid);
 public List<SectorMemberResponse> getTeacherInGroup(int groupid);
}
