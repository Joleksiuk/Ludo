package pl.rokolujka.springreactludo.authentication;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String nickname;

    public JwtResponse(String accessToken, Integer id, String nickname){
        this.token = accessToken;
        this.id = id;
        this.nickname=nickname;
    }

    public String getAccessToken(){
        return token;
    }

    public void setAccessToken(String accessToken){
        this.token=accessToken;
    }

    public String getTokenType(){
        return type;
    }

    public void setTokenType(String tokenType){
        this.type = tokenType;
    }

    public Integer getId(){
        return id;
    }
}
