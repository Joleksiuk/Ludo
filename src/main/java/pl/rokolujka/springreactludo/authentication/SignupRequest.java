package pl.rokolujka.springreactludo.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
    //@NotBlank
    String nickname;

    //@NotBlank
    String password;

}