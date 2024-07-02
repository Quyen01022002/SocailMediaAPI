package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PageMapper;
import social.media.media.model.mapper.PostMapper;
import social.media.media.model.mapper.UserMapper;
import social.media.media.model.reponse.*;
import social.media.media.service.PageService;
import social.media.media.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/save")
@RequiredArgsConstructor
public class PageController {
    @Autowired
    PageService pageService;
    @Autowired
    UserService userService;
    @Autowired
    PostMapper postMapper;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/add")
    public ApiResponse addMembers(@RequestParam("userId") int save, @RequestParam("postId") int post) {
        try {
            SaveItem pageMembers = new SaveItem();

            Post u = new Post();
            u.setId(post);
            Save user=pageService.findSave(save);
            pageMembers.setPage(user);
            pageMembers.setPost(u);
            pageService.addMemberPage(pageMembers);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }
    @GetMapping("/{id}")
    public ApiResponse<List<PostResponseDTO>> ListPostSaved(@PathVariable int id) {
        try {
            List<Post> post =new ArrayList<>();
            Save user=pageService.findSave(id);

            for(SaveItem item:user.getGroupMembers()) {
                post.add(item.getPost());

            }
            List<PostResponse> postResponseList = postMapper.toResponseList(post);
            for (int i=0; i< post.size(); i++){
                postResponseList.get(i).setCreateBy(userMapper.toResponsePost(post.get(i).getCreateBy()));
            }
            List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
            for (PostResponse itempost : postResponseList) {
                PostResponseDTO itemPostResponseDTO = new PostResponseDTO();
                itemPostResponseDTO.setId(itempost.getId());
                itemPostResponseDTO.setComment_count(itempost.getLisCmt().size());
                itemPostResponseDTO.setLike_count(itempost.getListLike().size());
                itemPostResponseDTO.setSave_count(itempost.getSave_count());
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
                for (SaveItem itemlike : itempost.getSaveItemList()) {
                    if (itemlike.getPage().getId() == id) {
                        itemPostResponseDTO.setUser_saved(true);
                        break;
                    }
                    itemPostResponseDTO.setUser_saved(false);
                }
                itemPostResponseDTO.setListAnh(itempost.getListAnh());
                itemPostResponseDTO.setStatus(itempost.getStatus());
                itemPostResponseDTO.setStatusViewPostEnum(itempost.getStatusViewPostEnum());
                itemPostResponseDTO.setStatusCmtPostEnum(itempost.getStatusCmtPostEnum());
                itemPostResponseDTO.setGroupname(itempost.getGroupname());
                postResponseDTOList.add(itemPostResponseDTO);
            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok( postResponseDTOList);
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }
    @DeleteMapping("")
    public ApiResponse DeleteCmt(@RequestParam("userId") int save, @RequestParam("postId") int post) {
        try {
            pageService.Delete(save,post);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok();
            return apiResponse;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage()); // Handle other exceptions
        }

    }

}
