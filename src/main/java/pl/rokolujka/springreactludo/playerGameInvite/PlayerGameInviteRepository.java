package pl.rokolujka.springreactludo.playerGameInvite;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerGameInviteRepository extends CrudRepository<PlayerGameInvite, PlayerGameInviteId>{
     List<PlayerGameInvite> findByGameId(Integer gameId);

     List<PlayerGameInvite> findByInvitedPlayerId(Integer invitingPlayerId);
}
