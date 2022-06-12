package pl.rokolujka.springreactludo.authentication;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    @Setter
    @Getter
    private String accessToken;
    @Setter
    @Getter
    private String tokenType = "Bearer";
    @Getter
    private final Integer id;
    @Getter
    private final String nickname;

    public JwtResponse(String accessToken, Integer id, String nickname){
        this.accessToken = accessToken;
        this.id = id;
        this.nickname=nickname;
    }
}
