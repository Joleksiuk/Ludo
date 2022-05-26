package pl.rokolujka.springreactludo.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    //@NotBlank
    private String nickname;

    //@NotBlank
    private String password;
}