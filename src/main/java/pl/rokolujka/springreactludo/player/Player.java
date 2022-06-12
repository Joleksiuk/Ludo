package pl.rokolujka.springreactludo.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "player",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "nickname")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nickname;
    String email;
    @JsonIgnore
    String passwordHash;

    String picture;

    public Player(String nickname,String email, String passwordHash, String picture) {
        this.nickname=nickname;
        this.email=email;
        this.passwordHash = passwordHash;
        this.picture = picture;
    }
}
