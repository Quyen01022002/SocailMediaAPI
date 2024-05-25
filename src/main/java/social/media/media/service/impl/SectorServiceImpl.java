package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;
import social.media.media.model.entity.User;
import social.media.media.model.mapper.SectorMapper;
import social.media.media.model.reponse.SectorMemberResponse;
import social.media.media.model.reponse.SectorResponse;
import social.media.media.model.request.SectorMemberRequest;
import social.media.media.repository.*;
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
    @Autowired
    private CommentRepository commentRepository;


    @Override
    public SectorResponse addSector(Sector sector) {
        Sector saved = sectorRepository.saveAndFlush(sector);
        return sectorMapper.toResponse(saved);
    }
    @Override
    public SectorResponse updateSector(Sector sector) {
        Sector saved = sectorRepository.saveAndFlush(sector);
        return sectorMapper.toResponse(saved);
    }
    @Override
    public void deleteSector(int sectorid) {
        try {
            Sector sector = sectorRepository.findById(sectorid).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (sector == null){
                throw new NotFoundException(" Not Found");
            }
            sectorRepository.delete(sector);
        }catch (ApplicationException ex) {
            throw ex;
        }

    }
    @Override
    public void deleteTeacherSector(int sectorid) {
        try {
            SectorMembers sector = sectorMembersRepository.findById(sectorid).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (sector == null){
                throw new NotFoundException(" Not Found");
            }
            sectorMembersRepository.delete(sector);
        }catch (ApplicationException ex) {
            throw ex;
        }

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
    public SectorMembers updateTeacherSector(SectorMembers sectorMember){
        SectorMembers rs = sectorMembersRepository.saveAndFlush(sectorMember);
        return rs;
    }
    @Override
    public SectorResponse getSector(int sectorid){
        Sector sector = sectorRepository.findById(sectorid).orElseThrow(() -> new NotFoundException("friend Not Found"));
        return sectorMapper.toResponse(sector);

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

    @Override
    public SectorMemberResponse getOneTeacherInGroup(int id){
        SectorMembers rs = sectorMembersRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
        return sectorMapper.toMemberResponse(rs);
    }
}
