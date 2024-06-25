package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import social.media.media.model.enums.GenderEnum;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import social.media.media.model.enums.RoleEnum;
import social.media.media.service.UserLevelService;

import java.sql.Date;
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
    private String fcm;

    private Boolean isEmail;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String profilePicture;
    private String backGroundPicture;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private Boolean isActived;
    private String address;
    private Date createdAt;
    private Date DoB;
    private Date updatedAt;
    @Column(name = "point", columnDefinition = "bigint default 0")
    private long point;

    @OneToOne(mappedBy = "userlevel", cascade = CascadeType.ALL)
    private UserLevel userLevel;

    @OneToMany(mappedBy = "CreateBy", fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Post> postList;

    @OneToMany(mappedBy = "UserReply", fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Post> postListReply;

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
    private List<Save> ListPageAdmin;
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Classes> ListTeacher;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<GroupMembers> groupMemberships;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<ClassMembers> classMembers;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<MessageMembers> messagesMembersList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<notications> noticationsList;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<SavedPost> savedPostList;
    @OneToMany(mappedBy = "reportedUserID", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Report> listReports;
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
        this.point=0;
    }
    @Transient
    UserLevelService userLevelService;
    @PreUpdate
    public void setModified(){

        this.updatedAt= new Date(System.currentTimeMillis());
//        List<UserLevel> list=userLevelService.findAll();
//        for(int i=0;i<=list.size();i++)
//        {
//            if(point>=list.get(i).getMinPoints()&&point<=list.get(i).getMaxPoints())
//            {
//                this.userLevel=list.get(i++);
//            }
//
//        }
    }
}
