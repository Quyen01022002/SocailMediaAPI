package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.model.entity.*;
import social.media.media.model.mapper.MessageMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.MessageBoxReponse;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.request.MessageBoxRequest;
import social.media.media.model.request.MessageRequest;
import social.media.media.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    MessageMapper messageMapper;



    @GetMapping("/{id}")
    public ApiResponse<List<MessageBoxReponse>> getBoxMessList(@PathVariable int id){
        List<MessageBox> list = messageService.getMessages(id);
        List<MessageBoxReponse> messageBoxReponseList=messageMapper.toListReponse(list);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(messageBoxReponseList);
        return apiResponse;
    }
    @GetMapping("/one/{id}")
    public ApiResponse<MessageBoxReponse> getBoxMess(@PathVariable int id){
        MessageBox list = messageService.getOneMessages(id);
        MessageBoxReponse messageBoxReponseList=messageMapper.toReponse(list);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(messageBoxReponseList);
        return apiResponse;
    }
    @PostMapping("/add")
    public ApiResponse<Message> post(@RequestBody MessageRequest post) {
        Message postEnity = messageMapper.toEntity(post);
        messageService.addMessages(postEnity);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(postEnity);
        return apiResponse;
    }
    @PostMapping("/single")
    public ApiResponse<MessageBoxReponse> post(@RequestBody MessageBoxRequest post,@RequestParam("id1") int id,@RequestParam("id") int UsserId2) {
        MessageBox postEnity = messageMapper.toEntity(post);
        User u=new User();
        u.setId(id);
        User u2=new User();
        u2.setId(UsserId2);
        MessageBox m=messageService.createMessageSg(postEnity,u,u2);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(messageMapper.toReponse(m));
        return apiResponse;
    }
}
