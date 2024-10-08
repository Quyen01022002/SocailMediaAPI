package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import social.media.media.model.enums.StatusCmtPostEnum;
import social.media.media.model.enums.StatusViewPostEnum;

import java.sql.Timestamp;
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
    @Column(columnDefinition = "NVARCHAR(255)")
    private String contentPost;

    private Timestamp timeStamp;

    private Boolean status;

    @Column(nullable = true)
    private Integer hotinday;

    @ManyToOne
    @JoinColumn(name = "saved_id")
    private Save page;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sectors;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classes;

    @Enumerated(EnumType.STRING)
    private StatusViewPostEnum statusViewPostEnum;

    @Enumerated(EnumType.STRING)
    private StatusCmtPostEnum statusCmtPostEnum;

    @ManyToOne
    @JoinColumn(name = "CreateBy", referencedColumnName = "id")
    private User CreateBy;

    @ManyToOne
    @JoinColumn(name = "UserReply", referencedColumnName = "id")
    private User UserReply;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<SaveItem> pageMemberships;
    @OneToMany(mappedBy = "listAnh", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    //@JsonBackReference
    private List<pictureOfPost> listAnh;

    @OneToMany(mappedBy = "postID", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Comments> lisCmt;

    @OneToMany(mappedBy = "postID", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<interations> listLike;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<SavedPost> savedPostList;
    @OneToMany(mappedBy = "reportedPostID", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Report> reportList;

    @PrePersist
    public void setCreate() {
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.CreateBy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
