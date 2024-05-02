package social.media.media.service;

import social.media.media.model.entity.Classes;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.User;

import java.util.List;

public interface MessageService {

    public List<MessageBox> getMessages(int idUser);
    public MessageBox createMessage(MessageBox messageBox, User user, Classes classes);
    public void addMembers(MessageBox messageBox,User user);
//    public List<MessageBoxResponse> getBoxMessList(int id);
}
