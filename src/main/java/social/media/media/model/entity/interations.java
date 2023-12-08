package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name ="interations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "interactionId")
public class interations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interactionId;

    @ManyToOne
    @JoinColumn(name = "postID", referencedColumnName = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Post postID;

    private Date timeStamp;

    private Boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "createBy", referencedColumnName = "id")
    @JsonManagedReference
    private User createBy;
    @PrePersist
    public void setCreate() {
        this.timeStamp = new Date(System.currentTimeMillis());
        this.createBy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.isLiked=true;
    }


}
