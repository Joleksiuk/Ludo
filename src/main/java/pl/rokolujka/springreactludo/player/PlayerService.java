package pl.rokolujka.springreactludo.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriendRepository;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInviteRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Player> findPlayerById(Integer id){
        return playerRepository.findById(id);
    }

    public List<Player> findAllFriendsOfPlayer(Integer id){
        List<Player> friendsOfPlayer = new LinkedList<>();
        playerFriendRepository.findAll().forEach(playerFriend -> {

            if(playerFriend.getFirstUserId().equals(id)){
                friendsOfPlayer.add(playerRepository.findById(playerFriend.getSecondUserId()).get());
            }
            else if(playerFriend.getSecondUserId().equals(id)){
                friendsOfPlayer.add(playerRepository.findById(playerFriend.getFirstUserId()).get());
            }
        });
        return friendsOfPlayer;
    }

    public List<Player> findAllSuggestedPlayerFriends(Integer id){
        List<Player> friendListOfPlayer =  findAllFriendsOfPlayer(id);
        List<Player> playersWhoCouldBeInvited = findAllPlayers();
        List<Player> playersAlreadyInvited = findAllFriendInvitedPlayersOfPlayer(id);
        playersWhoCouldBeInvited.remove(playerRepository.findById(id).get());

        friendListOfPlayer.forEach(player ->{
            playersWhoCouldBeInvited.remove(player);
        });
        playersAlreadyInvited.forEach(player -> {
            if(playersWhoCouldBeInvited.contains(player)){
                playersWhoCouldBeInvited.remove(player);
            }
        });

        return playersWhoCouldBeInvited ;
    }

    public List<Player> findAllFriendInvitedPlayersOfPlayer(Integer id){
        List<Player> friendInvitesOfPlayer = new LinkedList<>();
        playerFriendInviteRepository.findAll().forEach(friendInvite->{

            if(friendInvite.getInvitedUserId().equals(id)){
                friendInvitesOfPlayer.add(playerRepository.findById(friendInvite.getInvitingUserId()).get());
            }
            else if(friendInvite.getInvitingUserId().equals(id)){
                friendInvitesOfPlayer.add(playerRepository.findById(friendInvite.getInvitedUserId()).get());
            }

        });
        return friendInvitesOfPlayer;
    }
}
