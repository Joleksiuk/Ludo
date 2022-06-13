
package pl.rokolujka.springreactludo.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LobbyModel {

    Integer playerId;
    String color;
    String nickname;
    String picture;
}
