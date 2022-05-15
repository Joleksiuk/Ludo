package pl.rokolujka.springreactludo.playerGameInvite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "player_game_invite")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PlayerGameInviteId.class)
public class PlayerGameInvite {

    @Id
    Integer inviting_user_id;
    @Id
    Integer invited_user_id;
    @Id
    Integer game_id;

}

