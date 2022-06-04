package pl.rokolujka.springreactludo.player;

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
    String passwordHash;

    public Player(String nickname, String passwordHash) {
        this.nickname=nickname;
        this.passwordHash = passwordHash;
    }
}
