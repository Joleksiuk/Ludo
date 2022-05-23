package authentication;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    private String nickname;

    @NotBlank
    private String password_hash;

    public String getUsername() {
        return  nickname;
    }

    public void setUsername(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password_hash;
    }

    public void setPassword(String password_hash) {
        this.password_hash = password_hash;
    }

}