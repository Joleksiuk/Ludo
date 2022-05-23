package pl.rokolujka.springreactludo.game.pawn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pawn")
@IdClass(PawnId.class)
@Getter
public class Pawn {

    @Id
    private Integer number;
    @Id
    private Integer userId;
    @Id
    private Integer gameId;

    private Integer progress;
    private Boolean didStart;

    @Transient
    Integer fieldId;
}
