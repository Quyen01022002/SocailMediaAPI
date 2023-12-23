package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name ="MessageBox")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MessageBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @JoinColumn(name = "user_id")
    private int userId;


    @JoinColumn(name = "friend_id")
    private int friendId;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    private Date createdAt;
    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
    }
}
