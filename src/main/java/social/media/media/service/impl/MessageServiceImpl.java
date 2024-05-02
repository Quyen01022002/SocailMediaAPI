package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.MessageMembers;
import social.media.media.model.entity.User;
import social.media.media.repository.MessageMembersRepository;
import social.media.media.repository.MessageRepository;
import social.media.media.repository.UserRepository;
import social.media.media.repository.friendsRepository;
import social.media.media.service.MessageService;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMembersRepository messageMembersRepository;
    private final UserRepository userRepository;
    private final friendsRepository friendsRepositorys;


    @Override
    public List<MessageBox> getMessages(int idUser) {
        User user=new User();
        user.setId(idUser);
        List<MessageMembers> messageMembersList=messageMembersRepository.findByUser(user);
        List<MessageBox> messageBoxList=new ArrayList<>();
        for(MessageMembers messageMembers:messageMembersList){
            messageBoxList.add(messageMembers.getMessageBox());
        }
        return messageBoxList;
    }

    @Override
    public MessageBox createMessage(MessageBox messageBox, User user, Classes classes){
        try{
            messageBox.setClasses(classes);
            MessageBox messageBox1= messageRepository.saveAndFlush(messageBox);
            MessageMembers messageMembers=new MessageMembers();
            messageMembers.setMessageBox(messageBox1);
            messageMembers.setUser(user);

            messageMembersRepository.save(messageMembers);
            return messageBox1;

        }
        catch (ApplicationException ex){
            throw ex;
        }
    }

    @Override
    public void addMembers(MessageBox messageBox, User user) {
        MessageMembers messageMembers=new MessageMembers();
        messageMembers.setMessageBox(messageBox);
        messageMembers.setUser(user);
        messageMembersRepository.save(messageMembers);
    }


}
