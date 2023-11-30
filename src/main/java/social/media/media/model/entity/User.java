package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "\"user\"")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    @Column(unique=true)
    private String phone;

    private Boolean isPhone;

    @Column(unique=true)
    private String email;

    private Boolean isEmail;

    private String profilePicture;

    @Column(name = "password")
    private String password;

    private Boolean isActived;
    private String address;
    private Date createdAt;
    private Date updatedAt;


    @OneToMany(mappedBy = "CreateBy", fetch = FetchType.EAGER)
    private List<Post> postList;

    @OneToMany(mappedBy = "CreateBy", fetch = FetchType.EAGER)
    private List<Comments> cmtList;

    @OneToMany(mappedBy = "CreateBy", fetch = FetchType.EAGER)
    private List<interations> likeList;

    @OneToMany(mappedBy = "follower",fetch = FetchType.LAZY)
    private List<userFollow> following;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<friends> friendships;

    @OneToMany(mappedBy = "adminId", fetch = FetchType.LAZY)
    private List<Groups> ListGroupAdmin;
    @OneToMany(mappedBy = "adminId", fetch = FetchType.LAZY)
    private List<page> ListPageAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroupMembers> groupMemberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PageMembers> pageMemberships;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<notications> noticationsList;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(email));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
