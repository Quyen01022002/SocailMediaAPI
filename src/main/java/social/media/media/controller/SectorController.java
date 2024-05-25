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


    @PutMapping("/update")
    public ApiResponse<SectorResponse> updateSector(@RequestBody Sector sector){

        SectorResponse savedSector = sectorService.updateSector(sector);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(savedSector);
        return apiResponse;
    }

    @DeleteMapping("/{sectorid}")
    public ApiResponse<SectorResponse> deleteSector(@PathVariable int sectorid){

        sectorService.deleteSector(sectorid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok();
        return apiResponse;
    }

    @GetMapping("/{sectorid}")
    public ApiResponse<List<SectorResponse>> getSector(@PathVariable int sectorid){

        SectorResponse rs = sectorService.getSector(sectorid);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
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
    @PutMapping("/update/teacher/")
    public ApiResponse<SectorMembers> updateTeacherSector(@RequestBody SectorMembers sectorMember){

        SectorMembers rs = sectorService.updateTeacherSector(sectorMember);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;
    }

    @DeleteMapping("/update/teacher/{idteachersector}")
    public ApiResponse deleteTeacherSector(@PathVariable int idteachersector){

        sectorService.deleteTeacherSector(idteachersector);


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok();
        return apiResponse;
    }
    @GetMapping("/group/{idgroup}")
    public ApiResponse<List<SectorMemberResponse>> getTeacherInGroup(@PathVariable int idgroup){
        List<SectorMemberResponse> rs = sectorService.getTeacherInGroup(idgroup);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;

    }
    @GetMapping("/sectorTeacher/{id}")
    public ApiResponse<SectorMemberResponse> getOneSectorTeacher(@PathVariable int id){
        SectorMemberResponse rs = sectorService.getOneTeacherInGroup(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(rs);
        return apiResponse;

    }


}
