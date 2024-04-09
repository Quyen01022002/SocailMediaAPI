package social.media.media.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.LikeResponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.PostResponseDTO;
import social.media.media.service.PostService;

import java.util.ArrayList;
import java.util.List;
@Tag(name = "Authentication", description = "Authentication APIs")
@Controller
public class webController {

    @Autowired
    PostService postService;
    @MessageMapping("/hottop10")
    @SendTo("/topic/top10")
    public ApiResponse<List<PostResponseDTO>> getTop10WS(@PathVariable int id) {
        // Lấy danh sách top 10 từ cơ sở dữ liệu
        List<PostResponse> top10Posts = postService.getTop10();
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for (PostResponse itempost : top10Posts) {
            PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
            itemPostResponseDTO.setId(itempost.getId());
            itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
            itemPostResponseDTO.setLike_count(itempost.getListLike().size());
            itemPostResponseDTO.setCreateBy(itempost.getCreateBy());
            itemPostResponseDTO.setContentPost(itempost.getContentPost());
            itemPostResponseDTO.setTimeStamp(itempost.getTimeStamp());
            itemPostResponseDTO.setGroupid(itempost.getGroupid());
            for (LikeResponse itemlike : itempost.getListLike()) {
                if (itemlike.getCreateBy().getId() == id) {
                    itemPostResponseDTO.setUser_liked(true);
                    break;
                }
                itemPostResponseDTO.setUser_liked(false);
            }
            itemPostResponseDTO.setListAnh(itempost.getListAnh());
            itemPostResponseDTO.setStatus(itempost.getStatus());

            postResponseDTOList.add(itemPostResponseDTO);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postResponseDTOList);
        return apiResponse;
    }

    @MessageMapping("/update")
    @SendTo("/topic/update")
    public List<PostResponse> updateData() {
        // Lấy dữ liệu cập nhật từ DataService
        List<PostResponse>  updateMessage = postService.getTop10();
        return updateMessage;
    }


}


