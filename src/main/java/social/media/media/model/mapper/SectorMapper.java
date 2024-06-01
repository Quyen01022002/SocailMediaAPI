package social.media.media.model.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;
import social.media.media.model.reponse.SectorMemberResponse;
import social.media.media.model.reponse.SectorResponse;
import social.media.media.model.request.SectorMemberRequest;
import social.media.media.model.request.SectorRequest;
import social.media.media.model.request.SectorRequest2;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    @Mapping(source = "groupId", target = "groupId.id")
    Sector toSector(SectorRequest sectorRequest);

    @Mapping(source = "groupId.id", target = "groupId")
    @Mapping(source = "groupId.name", target = "nameGroup")
    @Mapping(source = "groupId.avatar", target = "avatarGroup")
    SectorResponse toResponse(Sector sector);

    List<SectorResponse> toListResponse(List<Sector> sectorList);

    @Mapping(source = "sectors.id", target = "sectorid")
    @Mapping(source = "sectors.name", target = "name")
    @Mapping(source = "sectors.avatar", target = "avatar")
    @Mapping(source = "user.id", target = "userid")
    @Mapping(source = "user.firstName", target = "first_name")
    @Mapping(source = "user.lastName", target = "last_name")
    @Mapping(source = "user.profilePicture", target = "avatarUser")
    SectorMemberResponse toMemberResponse(SectorMembers sectorMembers);

    List<SectorMemberResponse> toListMemberResponse (List<SectorMembers> membersList);

    @Mapping(source = "groupid", target = "groupId.id")
    Sector from2toSector (SectorRequest2 sectorRequest2);
}
