package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name ="notications")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class notications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String contentNotications;

    private Boolean isRead;
    private Timestamp timeStamp;


    @PrePersist
    public void setCreate() {
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.isRead = false;
    }

}
