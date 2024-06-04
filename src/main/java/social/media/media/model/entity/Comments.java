package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name ="Comments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "commentId")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "postID", referencedColumnName = "id")
    private Post postID;

    private Date timeStamp;

    private String commentContent;

    @OneToMany(mappedBy = "reportedCommentID", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> reportList;
    @ManyToOne
    @JoinColumn(name = "CreateBy", referencedColumnName = "id")
    private User CreateBy;
    
    private Boolean isAnwser;

}
