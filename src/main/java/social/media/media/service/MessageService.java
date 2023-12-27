package social.media.media.service;

import social.media.media.model.entity.MessageBox;
import social.media.media.model.reponse.MessageBoxResponse;

import java.util.List;

public interface MessageService {

    public List<MessageBox> getMessages(int idUser, int idFriend);
    public MessageBox createMessage(MessageBox messageBox);
    public MessageBox createFristMessage(int id);
    public List<MessageBoxResponse> getBoxMessList(int id);
}
