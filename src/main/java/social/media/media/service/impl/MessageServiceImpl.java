package social.media.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.media.media.exception.ApplicationException;
import social.media.media.model.entity.*;
import social.media.media.repository.*;
import social.media.media.service.MessageService;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMembersRepository messageMembersRepository;
    private final MessageItemRepository messageItemRepository;
    private final UserRepository userRepository;
    private final friendsRepository friendsRepositorys;


    @Override
    public List<MessageBox> getMessages(int idUser) {
        User user=new User();
        user.setId(idUser);
        List<MessageMembers> messageMembersList=messageMembersRepository.findByUser(user);
        List<MessageBox> messageBoxList=new ArrayList<>();
        List<MessageBox> messageBoxList2=messageRepository.findByUserOrUser1(user,user);
        for(MessageBox messageBox:messageBoxList2){
            if(messageBox.getUser().getId()==idUser)
            {
                if(messageBox.getUser1()!=null) {
                    messageBox.setAvatar(messageBox.getUser1().getProfilePicture());
                    messageBox.setName(messageBox.getUser1().getFirstName() + messageBox.getUser1().getLastName());
                }
                messageBoxList.add(messageBox);
            }
            else
            {
                messageBox.setAvatar(messageBox.getUser().getProfilePicture());
                messageBox.setName(messageBox.getUser().getFirstName()+messageBox.getUser().getLastName());
                messageBoxList.add(messageBox);

            }        }
        for(MessageMembers messageMembers:messageMembersList){
            messageBoxList.add(messageMembers.getMessageBox());
        }

        return messageBoxList;
    }

    @Override
    public MessageBox createMessage(MessageBox messageBox, User user, Classes classes){
        try{
            messageBox.setClasses(classes);
            messageBox.setUser(user);
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
    public MessageBox createMessageSg(MessageBox messageBox, User user,User u2){
        try{

            messageBox.setUser(user);
            messageBox.setUser1(u2);
            MessageBox m1=messageRepository.findByUserAndUser1(user,u2);
            if(m1==null) {
                MessageBox m2=messageRepository.findByUserAndUser1(u2,user);
                if(m2==null)
                {
                    MessageBox messageBox1= messageRepository.saveAndFlush(messageBox);
                    return messageBox1;
                }
                else
                {
                    return m2;
                }
            }
            else {
                return m1;

            }





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

    @Override
    public void addMessages(Message message) {
        try{
            messageItemRepository.save(message);
        }
        catch (ApplicationException ex){
            throw ex;
        }
    }

    @Override
    public MessageBox getOneMessages(int idMessage) {

        MessageBox messageMembersList=messageRepository.findById(idMessage).orElseThrow();

        return messageMembersList;
    }


}
