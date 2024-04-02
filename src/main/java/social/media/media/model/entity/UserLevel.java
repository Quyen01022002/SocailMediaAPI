package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "userLevel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userlevel;

    private int minPoints;
    private int maxPoints;
}
