package pl.rokolujka.springreactludo.playerFriend;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PlayerFriendRepository extends CrudRepository<PlayerFriend, PlayerFriendId> {
    List<PlayerFriend> findAllByFirstUserId(Integer id);
    List<PlayerFriend> findAllBySecondUserId(Integer id);
}
