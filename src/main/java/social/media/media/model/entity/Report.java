package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name ="Report")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "reporterID", referencedColumnName = "id")
    @JsonIgnore
    private User reporterID;
    @ManyToOne
    @JoinColumn(name = "reportedUserID", referencedColumnName = "id")
    @JsonIgnore
    private User reportedUserID;
    @ManyToOne
    @JoinColumn(name = "reportedPostID", referencedColumnName = "id")
    @JsonIgnore
    private Post reportedPostID;
    @ManyToOne
    @JoinColumn(name = "reportedCommentID", referencedColumnName = "commentId")
    @JsonIgnore
    private Comments reportedCommentID;

    private String reason;

    private Date timestamp;
    private boolean status;


    @PrePersist
    public void setCreate() {
        this.timestamp = new Date(System.currentTimeMillis());
        this.reporterID = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
