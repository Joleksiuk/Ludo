package pl.rokolujka.springreactludo.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;


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
    String password_hash;

    public Player(String nickname, String password_hash) {
        this.nickname=nickname;
        this.password_hash=password_hash;
    }
}
