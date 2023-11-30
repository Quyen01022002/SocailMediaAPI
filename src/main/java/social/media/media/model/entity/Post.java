package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name ="Post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contentPost;

    private Date timeStamp;


    //Thêm emun status
    private String status;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private page page;
    @ManyToOne
    @JoinColumn(name = "CreateBy", referencedColumnName = "id")
    private User CreateBy;

    @OneToMany(mappedBy = "listAnh", fetch = FetchType.EAGER)
    private List<pictureOfPost> listAnh;

    @OneToMany(mappedBy = "postID", fetch = FetchType.EAGER)
    private List<Comments> lisCmt;

    @OneToMany(mappedBy = "postID", fetch = FetchType.EAGER)
    private List<interations> listLike;

    @PrePersist
    public void setCreate() {
        this.timeStamp = new Date(System.currentTimeMillis());
        this.CreateBy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
