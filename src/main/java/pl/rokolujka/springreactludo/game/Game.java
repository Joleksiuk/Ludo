package pl.rokolujka.springreactludo.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String boardCode;
    private Integer currentDiceValue;
    private Integer turnPlayerId;
    private Integer winnerPlayerId;
    private boolean diceThrownInTurn;
    private Timestamp startDate;

}
