package social.media.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name ="pictureOfPost")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "picId")
public class pictureOfPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int picId;

    private String linkPicture;


    @ManyToOne
    @JoinColumn(name = "listAnh", referencedColumnName = "id")
    private Post listAnh;

}
