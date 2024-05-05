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
    @JoinColumn(name = "classId")
    @OneToOne
    private Classes classes;
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;
    @JoinColumn(name = "userId1")
    @ManyToOne
    private User user1;
    private String name;
    private String Avatar;
    @OneToMany(mappedBy = "messageBox", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MessageMembers> messageMembersList;
    @OneToMany(mappedBy = "messageBox", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Message> messagesList;

    private Date createdAt;
    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
    }
}
