package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.TournamentPlayer;
import fi.joonas.veikkaus.jpaentity.TournamentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentPlayerService {

	@Autowired
	TournamentPlayerDao tournamentPlayerDao;

	@Autowired
	TournamentTeamDao tournamentTeamDao;

	@Autowired
	PlayerDao playerDao;

	/**
	 * 
	 * @param tournamentPlayerGe
	 * @return
	 */
	public Long insert(TournamentPlayerGuiEntity tournamentPlayerGe) throws VeikkausServiceException {
		String tournamentTeamId = tournamentPlayerGe.getTournamentTeam().getId();
		Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId));
		if (!tournamentTeamDb.isPresent()) {
			throw new VeikkausServiceException(
					"Tournament team with id: " + tournamentTeamId + " wasn't found, insert failed");
		}

		String playerId = tournamentPlayerGe.getPlayer().getId();
		Optional<Player> playerDb = playerDao.findById(Long.valueOf(playerId));
		if (!playerDb.isPresent()) {
			throw new VeikkausServiceException("Player with id: " + playerId + " wasn't found, insert failed");
		} else {
		}

		tournamentPlayerGe.setTournamentTeam(TournamentTeamService.convertDbToGui(tournamentTeamDb.get()));
		tournamentPlayerGe.setPlayer(PlayerService.convertDbToGui(playerDb.get()));

		return tournamentPlayerDao.save(convertGuiToDb(tournamentPlayerGe)).getId();
	}

	/**
	 * 
	 * @param tournamentPlayerGe
	 * @return
	 */
	public Long modify(TournamentPlayerGuiEntity tournamentPlayerGe) throws VeikkausServiceException {
		String id = tournamentPlayerGe.getId();
		Optional<TournamentPlayer> tournamentPlayerDb = tournamentPlayerDao.findById(Long.valueOf(id));
		if (!tournamentPlayerDb.isPresent()) {
			throw new VeikkausServiceException("TournamentPlayer with id: " + id + " wasn't found, modify failed");
		}

		String tournamentTeamId = tournamentPlayerGe.getTournamentTeam().getId();
		Optional<TournamentTeam> tournamentTeamDb = tournamentTeamDao.findById(Long.valueOf(tournamentTeamId));
		if (!tournamentTeamDb.isPresent()) {
			throw new VeikkausServiceException("Tournament team with id: " + id + " wasn't found, modify failed");
		}

		String playerId = tournamentPlayerGe.getPlayer().getId();
		Optional<Player> playerDb = playerDao.findById(Long.valueOf(playerId));
		if (!playerDb.isPresent()) {
			throw new VeikkausServiceException("Player with id: " + id + " wasn't found, modify failed");
		}

		tournamentPlayerGe.setTournamentTeam(TournamentTeamService.convertDbToGui(tournamentTeamDb.get()));
		tournamentPlayerGe.setPlayer(PlayerService.convertDbToGui(playerDb.get()));

		return tournamentPlayerDao.save(convertGuiToDb(tournamentPlayerGe)).getId();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) throws VeikkausServiceException {
		boolean succeed;
		tournamentPlayerDao.deleteById(Long.valueOf(id));
		succeed = true;
		return succeed;
	}

	/**
	 * 
	 * @return
	 */
	public List<TournamentPlayerGuiEntity> findAllTournamentPlayers() {
		List<TournamentPlayerGuiEntity> geList = new ArrayList<>();
		List<TournamentPlayer> dbTournPlayers = ImmutableList.copyOf(tournamentPlayerDao.findAll());

		for (TournamentPlayer dbTournPlayer : dbTournPlayers) {
			geList.add(convertDbToGui(dbTournPlayer));
		}

		return geList;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public TournamentPlayerGuiEntity findOneTournamentPlayer(String id) {
		TournamentPlayerGuiEntity tournPlayerGe = convertDbToGui(tournamentPlayerDao.findById(Long.valueOf(id)).get());
		return tournPlayerGe;
	}

	protected static TournamentPlayerGuiEntity convertDbToGui(TournamentPlayer db) {
		TournamentPlayerGuiEntity ge = new TournamentPlayerGuiEntity();

		ge.setId(db.getId().toString());
		ge.setTournamentTeam(TournamentTeamService.convertDbToGui(db.getTournamentTeam()));
		ge.setPlayer(PlayerService.convertDbToGui(db.getPlayer()));
		ge.setGoals(Integer.valueOf(db.getGoals()).toString());
		return ge;
	}

	protected static TournamentPlayer convertGuiToDb(TournamentPlayerGuiEntity ge) {
		TournamentPlayer db = new TournamentPlayer();

		if (ge.getId() != null && !ge.getId().isEmpty()) {
			db.setId(Long.valueOf(ge.getId()));
		} else {
			db.setId(null);
		}
		db.setTournamentTeam(TournamentTeamService.convertGuiToDb(ge.getTournamentTeam()));
		db.setPlayer(PlayerService.convertGuiToDb(ge.getPlayer()));
		db.setGoals(Integer.valueOf(ge.getGoals()));

		return db;
	}

}