package pl.rokolujka.springreactludo.playerFriend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_friend")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PlayerFriendId.class)
public class PlayerFriend implements Serializable {

    @Id
    Integer first_user_id;
    @Id
    Integer second_user_id;
}
