package pl.rokolujka.springreactludo.playerFriendInvite;

import org.springframework.data.repository.CrudRepository;


import java.util.List;


public interface PlayerFriendInviteRepository extends CrudRepository<PlayerFriendInvite, PlayerFriendInviteId>{
    List<PlayerFriendInvite> findAllByInvitingUserId(Integer id);
    List<PlayerFriendInvite> findAllByInvitedUserId(Integer id);
}
