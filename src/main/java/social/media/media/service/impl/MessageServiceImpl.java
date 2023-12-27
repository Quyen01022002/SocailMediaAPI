package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.media.media.controller.FriendsController;
import social.media.media.exception.ApplicationException;
import social.media.media.exception.NotFoundException;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.User;
import social.media.media.model.entity.friends;
import social.media.media.model.reponse.MessageBoxResponse;
import social.media.media.repository.MessageRepository;
import social.media.media.repository.UserRepository;
import social.media.media.repository.friendsRepository;
import social.media.media.service.MessageService;
import social.media.media.service.PageService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final friendsRepository friendsRepositorys;
    @Override
    public MessageBox createMessage(MessageBox messageBox){
        try{
            return messageRepository.saveAndFlush(messageBox);

        }
        catch (ApplicationException ex){
            throw ex;
        }
    }
    @Override
    public MessageBox createFristMessage(int id){
        try{
            friends friend = friendsRepositorys.findById(id).orElseThrow(() -> new NotFoundException(" Not Found"));
            MessageBox messageBox = new MessageBox();
            messageBox.setUserId(friend.getUser().getId());
            messageBox.setFriendId(friend.getFriend().getId());
            messageBox.setContent("Bây giờ chúng ta đã là bạn bè!");
            messageBox.setId(0);
            messageBox.setCreatedAt(new Date(System.currentTimeMillis()));
            return messageRepository.saveAndFlush(messageBox);

        }
        catch (ApplicationException ex){
            throw ex;
        }
    }

    @Override
    public List<MessageBox> getMessages(int idUser, int idFriend){
        try{
            List<MessageBox> list1 = messageRepository.findByUserIdAndFriendId(idUser, idFriend);
            List<MessageBox> list2 = messageRepository.findByUserIdAndFriendId(idFriend, idUser);
            List<MessageBox> mergedList = new ArrayList<>();
            mergedList.addAll(list1);
            mergedList.addAll(list2);
            Collections.sort(mergedList, Comparator.comparingInt(MessageBox::getId));
            return mergedList;

        }
        catch (ApplicationException ex){
            throw ex;
        }
    }

    @Override
    public List<MessageBoxResponse> getBoxMessList(int id){
        try{
            List<MessageBox> list = messageRepository.loadListNewMessUser(id);
            List<MessageBoxResponse> listresult = new ArrayList<>();
            for (MessageBox messageBox : list) {
                MessageBoxResponse response = new MessageBoxResponse();
                // Điền thông tin từ MessageBox vào MessageBoxResponse
                response.setId(messageBox.getId());
                response.setUserId(messageBox.getUserId());
                response.setFriendId(messageBox.getFriendId());
                response.setNewest_mess(messageBox.getContent());
                response.setCreatedAt(messageBox.getCreatedAt());
                User user;
                if (id==messageBox.getUserId()){
                    user = userRepository.findById(messageBox.getFriendId()).orElseThrow(() -> new NotFoundException(" Not Found"));}
                else  {
                    user = userRepository.findById(messageBox.getUserId()).orElseThrow(() -> new NotFoundException(" Not Found"));
                }
                response.setFriendName(user.getFirstName()+user.getLastName());
                response.setFriendAvatar(user.getProfilePicture());
                // Thêm response vào danh sách kết quả
                listresult.add(response);
            }
            Collections.sort(listresult, Comparator.comparingInt(MessageBoxResponse::getId).reversed());
             return listresult;

        }
        catch (ApplicationException ex){
            throw ex;
        }
    }
}
