package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;

@Setter
@Getter
@Entity
@Table(name ="friends")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User friend;

    private Boolean status;
    private Date createdAt;

    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
     
    }
}


