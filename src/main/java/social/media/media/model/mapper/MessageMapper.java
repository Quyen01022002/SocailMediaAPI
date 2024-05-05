package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.Message;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.entity.MessageMembers;
import social.media.media.model.reponse.ClassResponse;
import social.media.media.model.reponse.MessageBoxReponse;
import social.media.media.model.reponse.MessageMembersReponse;
import social.media.media.model.reponse.MessageReponse;
import social.media.media.model.request.ClassRequest;
import social.media.media.model.request.MessageBoxRequest;
import social.media.media.model.request.MessageRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MessageMapper {


    MessageBox toEntity(MessageBoxRequest messageBoxRequest);

    @Mapping(source = "messageBox", target = "messageBox.id")
    Message toEntity(MessageRequest messageBoxRequest);

    List<MessageBoxReponse> toListReponse(List<MessageBox> messageBoxList);

    MessageBoxReponse toReponse(MessageBox messageBoxList);

    List<MessageMembersReponse> toListMembersReponse(List<MessageMembers> messageBoxList);

    List<MessageReponse> toListMesageReponse(List<Message> messageBoxList);


}
