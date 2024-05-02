package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name ="groups_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id")
    private User adminId;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;

    private String description;
    private String Avatar;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<GroupMembers> groupMembers;
    @OneToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> listPost;
    @OneToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Classes> classesList;
    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
        this.adminId = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    @PreUpdate
    public void setModified(){
        this.updatedAt= new Date(System.currentTimeMillis());
    }
}
