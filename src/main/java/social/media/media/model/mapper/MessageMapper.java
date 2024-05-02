package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.media.media.model.entity.Classes;
import social.media.media.model.entity.MessageBox;
import social.media.media.model.reponse.ClassResponse;
import social.media.media.model.reponse.MessageBoxReponse;
import social.media.media.model.request.ClassRequest;
import social.media.media.model.request.MessageBoxRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MessageMapper {


    MessageBox toEntity(MessageBoxRequest messageBoxRequest);
    List<MessageBoxReponse> toListReponse(List<MessageBox> messageBoxList);

}
