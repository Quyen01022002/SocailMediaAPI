package social.media.media.service;

import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;
import social.media.media.model.reponse.SectorMemberResponse;
import social.media.media.model.reponse.SectorResponse;
import social.media.media.model.request.SectorMemberRequest;

import java.util.List;

public interface SectorService {
 public SectorResponse addSector(Sector sector);
 public SectorResponse updateSector(Sector sector);
 public void deleteSector(int sectorid);
 public SectorMembers addTeacherSector(SectorMemberRequest sectorMemberRequest);
 public SectorMembers updateTeacherSector(int id, SectorMemberRequest sectorMember);
 public void deleteTeacherSector(int idsectorteacher);

 public List<SectorResponse> getSectorInGroup(int groupid);
 public SectorResponse getSector(int sectorid);
 public List<SectorMemberResponse> getTeacherInGroup(int groupid);
 public SectorMemberResponse getOneTeacherInGroup(int id);
}
