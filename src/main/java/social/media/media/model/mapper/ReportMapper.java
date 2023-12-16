package social.media.media.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import social.media.media.model.entity.Groups;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Report;
import social.media.media.model.entity.User;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.ReportReponse;
import social.media.media.model.reponse.UserResponse;
import social.media.media.model.request.PostRequest;
import social.media.media.model.request.UserRequest;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportReponse toResponse(Report report);
    //Report toEntity(UserRequest userEntity);
    List<ReportReponse> toResponseList(List<Report> reportList);
}
