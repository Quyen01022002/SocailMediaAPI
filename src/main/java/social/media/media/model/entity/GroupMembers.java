package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name ="GroupMembers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GroupMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private Date createdAt;
    private Date updatedAt;
    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
    }
}
