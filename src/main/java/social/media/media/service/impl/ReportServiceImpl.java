package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.mapper.ReportMapper;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.model.reponse.ReportReponse;

import social.media.media.repository.*;
import social.media.media.service.ReportService;
import social.media.media.service.SavedPostService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SavedPostRepository savedPostRepository;
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ReportReponse reportUser(Report report) {
        return null;
    }

    @Override
    public void reportPost(Report report) {
        try {
            reportRepository.saveAndFlush(report);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void Delete(int id) {
        try {
            Report report = reportRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (report == null) {
                throw new NotFoundException("Not Found");
            }
            reportRepository.delete(report);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public PostResponse DuyetPost(int id) {
        try {
            Post exPost = postRepository.findById(id).orElseThrow(() -> new NotFoundException("friend Not Found"));
            if (exPost == null) {
                throw new NotFoundException("Not Found");
            }
            exPost.setStatus(true);
            // Update
            postRepository.saveAndFlush(exPost);
            // Map to Rsponse
            return postMapper.toResponse(exPost);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<ReportReponse> listReportPost(int report) {
        Groups g=new Groups();
        g.setId(report);
        List<Report> list = postRepository.findReportedPostsByGroup(g);
        List<ReportReponse> postResponseDTOList=new ArrayList<>();
        for(Report item:list)
        {

            ReportReponse fp=reportMapper.toResponse(item);



            postResponseDTOList.add(fp);
        }




        return postResponseDTOList;
    }

    @Override
    public List<PostResponseDTO> listPosstWaiting(int groupid) {
        Groups g=new Groups();
        g.setId(groupid);
        List<Post> list = postRepository.findByGroupsAndStatus(g,false);
        List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
        for(Post item:list)
        {

            PostResponse fp=postMapper.toResponse(item);
            PostResponseDTO itemPostResponseDTO=new PostResponseDTO();
            itemPostResponseDTO.setSavedId(item.getId());
            itemPostResponseDTO.setId(fp.getId());
            itemPostResponseDTO.setComment_count(fp.getLisCmt().size());
            itemPostResponseDTO.setCreateBy(fp.getCreateBy());
            itemPostResponseDTO.setLike_count(fp.getListLike().size());
            itemPostResponseDTO.setContentPost(fp.getContentPost());
            itemPostResponseDTO.setTimeStamp(fp.getTimeStamp());
//            for(LikeResponse itemlike:  fp.getListLike())
//            {
//                if(itemlike.getCreateBy().getId()==id)
//                {
//                    itemPostResponseDTO.setUser_liked(true);
//
//                }
//                else {
//                    itemPostResponseDTO.setUser_liked(false);
//                }
//            }
            itemPostResponseDTO.setListAnh(fp.getListAnh());
            itemPostResponseDTO.setStatus(fp.getStatus());

            postResponseDTOList.add(itemPostResponseDTO);
        }

        return postResponseDTOList;
    }
    @Override
    public Report searchByIdUserReporterAndPost(int idreporter, int postid){
       try {
           List<Report> report = reportRepository.findByUserIdAndPostId(idreporter,postid);
           if (report.size() == 0)
               return null;
           else return report.get(0);
       }
catch (Exception e)
{
    throw  e;
}
    }
}
