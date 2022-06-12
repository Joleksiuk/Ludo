package pl.rokolujka.springreactludo.game.gamePlayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player_game")
@IdClass(GamePlayerId.class)
@Getter
@Setter
public class GamePlayer {

    @Id
    private Integer playerId;
    @Id
    private Integer gameId;

    private String playerColour;

    private Integer nextPlayerId;

}
