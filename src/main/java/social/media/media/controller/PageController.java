package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.PageMapper;
import social.media.media.model.mapper.PostMapper;
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
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok( postMapper.toResponseDTO(post));
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
