package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Table(name ="ClassMembers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClassMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    private Date createdAt;
    private Date updatedAt;

    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt= new Date(System.currentTimeMillis());
    }
}
