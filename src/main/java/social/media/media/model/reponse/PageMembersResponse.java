package social.media.media.model.reponse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import social.media.media.model.entity.Post;
import social.media.media.model.entity.Save;

import java.sql.Date;

@Getter
@Setter
public class PageMembersResponse {

    private int id;


    private PostResponse post;

    private SaveResponse page;

    private Date createdAt;
    private Date updatedAt;



}
