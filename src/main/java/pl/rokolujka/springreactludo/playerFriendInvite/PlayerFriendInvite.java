package pl.rokolujka.springreactludo.playerFriendInvite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "player_friend_invite")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PlayerFriendInviteId.class)
public class PlayerFriendInvite{

    @Id
    Integer invitingPlayerId;
    @Id
    Integer invitedPlayerId;
}
