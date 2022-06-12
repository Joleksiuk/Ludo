package pl.rokolujka.springreactludo.authentication;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.rokolujka.springreactludo.player.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private static Collection<? extends GrantedAuthority> authorities;

    private Integer id;

    private String nickname;

    private String email;

    private String picture;

    @JsonIgnore
    private String password_hash;

    public PlayerDetailsImpl(Integer id, String nickname, String email, String picture, String password_hash,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password_hash = password_hash;
        this.picture = picture;
        this.authorities = authorities;
    }

    public static PlayerDetailsImpl build(Player player) {

        return new PlayerDetailsImpl(
                player.getId(),
                player.getNickname(),
                player.getEmail(),
                player.getPicture(),
                player.getPasswordHash(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password_hash;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlayerDetailsImpl player = (PlayerDetailsImpl) o;
        return Objects.equals(id, player.id);
    }
}
