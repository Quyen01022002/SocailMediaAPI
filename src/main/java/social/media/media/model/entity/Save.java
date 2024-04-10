package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name ="Saves")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id")
    @JsonIgnore
    private User adminId;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SaveItem> groupMembers;

    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt= new Date(System.currentTimeMillis());
    }

    @PreUpdate
    public void setModified(){
        this.updatedAt= new Date(System.currentTimeMillis());
    }
}
