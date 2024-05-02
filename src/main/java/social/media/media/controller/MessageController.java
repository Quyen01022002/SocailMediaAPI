package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.mapper.MessageMapper;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.MessageBoxReponse;
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
}
