package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;
import social.media.media.model.entity.User;
import social.media.media.model.mapper.SectorMapper;
import social.media.media.model.reponse.SectorMemberResponse;
import social.media.media.model.reponse.SectorResponse;
import social.media.media.model.request.SectorMemberRequest;
import social.media.media.repository.GroupsRepository;
import social.media.media.repository.SectorMembersRepository;
import social.media.media.repository.SectorRepository;
import social.media.media.repository.UserRepository;
import social.media.media.service.SectorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {


    private final SectorRepository sectorRepository;
private  final GroupsRepository groupsRepository;
private final UserRepository userRepository;
private final SectorMembersRepository sectorMembersRepository;
    @Autowired
    SectorMapper sectorMapper;


    @Override
    public SectorResponse addSector(Sector sector) {
        Sector saved = sectorRepository.saveAndFlush(sector);
        return sectorMapper.toResponse(saved);
    }

    @Override
    public SectorMembers addTeacherSector(SectorMemberRequest sectorMemberRequest){
        SectorMembers sectorMembers = new SectorMembers();
        Sector sector = sectorRepository.findById(sectorMemberRequest.getSectorid()).orElseThrow(() -> new NotFoundException("friend Not Found"));
        User user = userRepository.findById(sectorMemberRequest.getUserid()).orElseThrow(() -> new NotFoundException("friend Not Found"));
        sectorMembers.setSectors(sector);
        sectorMembers.setUser(user);
        SectorMembers rs = sectorMembersRepository.saveAndFlush(sectorMembers);
        return rs;
    }

    @Override
    public List<SectorResponse> getSectorInGroup(int groupid) {
        Groups exGroups = groupsRepository.findById(groupid).orElseThrow(() -> new NotFoundException("friend Not Found"));
        List<Sector> list = sectorRepository.findByGroupId(exGroups);
        return sectorMapper.toListResponse(list);
    }
@Override
    public List<SectorMemberResponse> getTeacherInGroup(int groupid){
        List<SectorMembers> rs = sectorMembersRepository.findAllByGroupId(groupid);
        return sectorMapper.toListMemberResponse(rs);
    }
}
