package social.media.media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.MessageBoxResponse;
import social.media.media.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/")
    public ApiResponse<MessageBox> post(@RequestBody MessageBox messageBox){
        MessageBox messageBox1 = messageService.createMessage(messageBox);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(messageBox1);
        return apiResponse;

    }

    @GetMapping("/")
    public ApiResponse<List<MessageBox>> getMessage(@RequestParam int userId, @RequestParam int friendId){
        List<MessageBox> list = messageService.getMessages(userId,friendId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(list);
        return apiResponse;

    }

    @GetMapping("/{id}")
    public ApiResponse<List<MessageBoxResponse>> getBoxMessList(@PathVariable int id){
        List<MessageBoxResponse> list = messageService.getBoxMessList(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(list);
        return apiResponse;
    }
}
