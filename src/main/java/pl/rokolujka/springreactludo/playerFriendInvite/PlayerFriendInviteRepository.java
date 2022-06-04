package pl.rokolujka.springreactludo.playerFriendInvite;

import org.springframework.data.repository.CrudRepository;


import java.util.List;


public interface PlayerFriendInviteRepository extends CrudRepository<PlayerFriendInvite, PlayerFriendInviteId>{
    List<PlayerFriendInvite> findAllByInvitingPlayerId(Integer id);
    List<PlayerFriendInvite> findAllByInvitedPlayerId(Integer id);
}
