package pl.rokolujka.springreactludo.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.integration.GravatarService;
import pl.rokolujka.springreactludo.integration.MD5;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    GravatarService gravatarService;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNickname(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        PlayerDetailsImpl playerDetails = (PlayerDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                playerDetails.getId(),
                playerDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerPlayer(@Valid @RequestBody SignupRequest signUpRequest) {
        if (playerRepository.existsByNickname(signUpRequest.getNickname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Nick name is already taken!"));
        }

        Player player = new Player(null, signUpRequest.getNickname(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), gravatarService.findGravatarUrl(signUpRequest.getEmail()).orElse(null));

        playerRepository.save(player);

        return ResponseEntity.ok(new MessageResponse("Player registered successfully!"));
    }
}