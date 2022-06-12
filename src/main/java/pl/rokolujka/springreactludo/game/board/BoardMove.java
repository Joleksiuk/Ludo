package pl.rokolujka.springreactludo.game.board;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Builder
@Value
public class BoardMove implements Serializable {
    Integer fromFieldId;
    Integer toFieldId;
}
