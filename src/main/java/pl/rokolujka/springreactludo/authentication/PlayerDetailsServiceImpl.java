package pl.rokolujka.springreactludo.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerRepository;

@Service
public class PlayerDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PlayerRepository playerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerRepository.findByNickname(username)
                .orElseThrow(()-> new UsernameNotFoundException("Player Not Found with username "+ username));
        return PlayerDetailsImpl.build(player);
    }
}
