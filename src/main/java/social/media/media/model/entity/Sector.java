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
@Table(name ="sectors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name ="groupId", referencedColumnName = "id")
    private Groups groupId;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;

    private String Avatar;

    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "sectors", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> listPost;

    @OneToMany(mappedBy = "sectors", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SectorMembers> sectorMembers;


    @PrePersist
    public void setCreate(){
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date (System.currentTimeMillis());

    }






}
