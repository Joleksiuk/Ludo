package pl.rokolujka.springreactludo.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriend;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriendRepository;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInviteRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerFriendRepository playerFriendRepository;
    private final PlayerFriendInviteRepository playerFriendInviteRepository;

    public List<Player> findAllPlayers() {
        List<Player> players = new LinkedList<>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    public void deletePlayerById(Integer id) {
        playerRepository.deleteById(id);
    }

    public Optional<Player> findPlayerById(Integer id) {
        return playerRepository.findById(id);
    }

    public List<Player> findAllFriendsOfPlayer(Integer id) {
        List<PlayerFriend> friendListOfPlayer = playerFriendRepository.findAllByFirstPlayerId(id);
        List<PlayerFriend> friendSecondOfPlayer = playerFriendRepository.findAllBySecondPlayerId(id);

        List<Integer> firstPlaceIds = friendListOfPlayer.stream()
                .map(PlayerFriend::getSecondPlayerId)
                .collect(Collectors.toList());
        List<Integer> secondPlaceIds = friendSecondOfPlayer.stream()
                .map(PlayerFriend::getFirstPlayerId)
                .collect(Collectors.toList());

        firstPlaceIds.addAll(secondPlaceIds);
        return firstPlaceIds
                .stream()
                .map(playerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<Player> findAllSuggestedPlayerFriends(Integer id) {
        List<Player> players = findAllPlayers();
        players.removeAll(findAllFriendsOfPlayer(id));
        players.removeAll(findAllFriendInvitedPlayersOfPlayer(id));
        players.remove(findPlayerById(id).orElseThrow());

        return players;
    }

    public List<Player> findAllFriendInvitedPlayersOfPlayer(Integer id) {
        List<PlayerFriendInvite> firstIdInvites = playerFriendInviteRepository.findAllByInvitingPlayerId(id);
        List<PlayerFriendInvite> secondIdInvites = playerFriendInviteRepository.findAllByInvitedPlayerId(id);

        List<Integer> firstPlaceIds = firstIdInvites.stream().map(PlayerFriendInvite::getInvitedPlayerId).collect(Collectors.toList());
        List<Integer> secondPlaceIds = secondIdInvites.stream().map(PlayerFriendInvite::getInvitingPlayerId).collect(Collectors.toList());

        firstPlaceIds.addAll(secondPlaceIds);

        return firstPlaceIds
                .stream()
                .map(playerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Optional<Player> findPlayerByNickname(String nickname) {
        return playerRepository.findByNickname(nickname);
    }
}
