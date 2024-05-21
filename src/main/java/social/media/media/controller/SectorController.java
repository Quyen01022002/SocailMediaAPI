package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.model.entity.Sector;
import social.media.media.model.entity.SectorMembers;
import social.media.media.model.mapper.SectorMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.SectorMemberResponse;
import social.media.media.model.reponse.SectorResponse;
import social.media.media.model.request.SectorMemberRequest;
import social.media.media.model.request.SectorRequest;
import social.media.media.service.SectorService;

import java.util.List;

@RestController
@RequestMapping("/sector")
@RequiredArgsConstructor
public class SectorController {

    @Autowired
    SectorService sectorService;
    @Autowired
    SectorMapper sectorMapper;



    @PostMapping("/")
    public ApiResponse<SectorResponse> addSector(@RequestBody SectorRequest sectorRequest){
        Sector sector = sectorMapper.toSector(sectorRequest);
        SectorResponse savedSector = sectorService.addSector(sector);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedSector);
        return apiResponse;
    }

    @GetMapping("/allInOneGroup/{groupid}")
    public ApiResponse<List<SectorResponse>> getSectorInOneGroup(@PathVariable int groupid){

        List<SectorResponse> rs = sectorService.getSectorInGroup(groupid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;
    }

    @PostMapping("/add/teacher")
    public ApiResponse<SectorMembers> addTeacherSector(@RequestBody SectorMemberRequest sectorMemberRequest){

        SectorMembers rs = sectorService.addTeacherSector(sectorMemberRequest);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;
    }

    @GetMapping("/group/{idgroup}")
    public ApiResponse<List<SectorMemberResponse>> getTeacherInGroup(@PathVariable int idgroup){
        List<SectorMemberResponse> rs = sectorService.getTeacherInGroup(idgroup);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;

    }


}
