package pl.rokolujka.springreactludo.playerFriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerFriendService {

    private final PlayerFriendRepository playerFriendRepository;

    public List<PlayerFriend> findAllPlayerFriends() {
        List<PlayerFriend> playerFriends = new LinkedList<>();
        playerFriendRepository.findAll().forEach(playerFriends::add);
        return playerFriends;
    }

    public void createPlayerFriend(PlayerFriend playerFriend) {
        playerFriendRepository.save(playerFriend);
    }

    public void deletePlayerFriend(PlayerFriend playerFriend) {playerFriendRepository.delete(playerFriend);}

}
