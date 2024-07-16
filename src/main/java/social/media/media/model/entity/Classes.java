package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name ="classes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id")
    @JsonIgnore
    private User teacher;
    private String name;
    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    @JsonIgnore
    private Groups groups;
    private String description;
    private String Avatar;
    private String BackAvatar;
    private Date createdAt;
    private Date updatedAt;
    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> listPost;
    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ClassMembers> classMembers;


    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.teacher = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.updatedAt= new Date(System.currentTimeMillis());
    }

    @PreUpdate
    public void setModified(){
        this.updatedAt= new Date(System.currentTimeMillis());
    }
}
