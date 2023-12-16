package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import social.media.media.model.enums.GenderEnum;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    @Column(columnDefinition = "NVARCHAR(255)")
    private String firstName;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String lastName;

    @Column(unique = true)
    private String phone;

    private Boolean isPhone;

    @Column(unique = true)
    private String email;
    private String FCM;

    private Boolean isEmail;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String profilePicture;
    private String backGroundPicture;

    @Column(name = "password")
    private String password;

    private Boolean isActived;
    private String address;
    private Date createdAt;
    private Date DoB;
    private Date updatedAt;


    @OneToMany(mappedBy = "CreateBy", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Post> postList;

    @OneToMany(mappedBy = "CreateBy", fetch = FetchType.LAZY)
    private List<Comments> cmtList;

    @OneToMany(mappedBy = "createBy", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<interations> likeList;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<userFollow> following;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<friends> friendships;
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<friends> Otherfriendships;

    @OneToMany(mappedBy = "adminId", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Groups> ListGroupAdmin;
    @OneToMany(mappedBy = "adminId", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<page> ListPageAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<GroupMembers> groupMemberships;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<PageMembers> pageMemberships;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    public int countMutualFriends(User otherUser) {
        if (friendships != null && otherUser.getFriendships() != null) {
            List<Integer> user1FriendIds = friendships.stream().map(friend -> friend.getFriend().getId()).collect(Collectors.toList());
            List<Integer> user2FriendIds = otherUser.getFriendships().stream().map(friend -> friend.getFriend().getId()).collect(Collectors.toList());

            return (int) user1FriendIds.stream().filter(user2FriendIds::contains).count();
        }

        return 0;
    }
    @PrePersist
    public void setCreate() {
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
    }
    @PreUpdate
    public void setModified(){
        this.updatedAt= new Date(System.currentTimeMillis());
    }
}
