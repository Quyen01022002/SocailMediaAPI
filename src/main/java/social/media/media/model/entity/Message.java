package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name ="Message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @JoinColumn(name = "message_id")
    @ManyToOne
    private MessageBox messageBox;

    @Column(columnDefinition = "NVARCHAR(200)")
    private String content;

    private Date createdAt;
    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}
