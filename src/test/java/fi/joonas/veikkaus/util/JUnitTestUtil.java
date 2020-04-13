package fi.joonas.veikkaus.util;

import fi.joonas.veikkaus.config.VeikkausServerProperties;
import fi.joonas.veikkaus.dao.*;
import fi.joonas.veikkaus.jpaentity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fi.joonas.veikkaus.constants.VeikkausConstants.STATUS_UNDER_WORK;

public abstract class JUnitTestUtil {
	
	@Autowired
	private
	VeikkausServerProperties veikkausServerProperties;
	
	private static final Logger logger = LoggerFactory.getLogger(JUnitTestUtil.class);
	
	public static final boolean CLEAN_BEFORE_RUN_JUNIT_TESTS = false;
	public static final String CHARSET = java.nio.charset.StandardCharsets.UTF_8.name();
	
	public Map<String, String> paramMap;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StatusDao statusDao;
	
	@Autowired
	private BetDao betDao;
	
	@Autowired
	private TournamentDao tournamentDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private TournamentTeamDao tournamentTeamDao;
	
	@Autowired
	private PlayerDao playerDao;
	
	@Autowired
	private TournamentPlayerDao tournamentPlayerDao;
	
	@Autowired
	private GameDao gameDao;
	
	@Autowired
	private ScorerDao scorerDao;
	
	@Autowired
	private BetResultDao betResultDao;
	
	private String getServerUrl() {
		return veikkausServerProperties.getProtocol() + "://" + veikkausServerProperties.getHostPort() +
				"/" + veikkausServerProperties.getApplicationName();
	}
	
	/**
	 * 
	 * @param url URL called
	 * @param getId If true, id is parsed from response and returned
	 * @return Id if getId == true. Empty value if getId == false.
	 * @throws Exception
	 */
	public String callUrl(String url, boolean getId) throws Exception {
		String id = "";

		InputStream response;
		try {
			String serverUrl = getServerUrl();
			response = new URL(serverUrl + url).openStream();
		} catch (IOException e) {
			logger.error("Getting response from URL: " + url + " failed: ", e);
			throw e;
		}

		String responseBody = null;
		try (Scanner scanner = new Scanner(response)) {
			responseBody = scanner.useDelimiter("\\A").next();
			logger.info("URL: " + url + " produced responseBody: " + responseBody);
		}

		if (getId) {
			Pattern p = Pattern.compile("(\\d+)");
			Matcher matcher = p.matcher(responseBody);

			if (matcher.find()) {
				logger.info("id found: " + matcher.group(1));
				id = matcher.group(1);
			}
		}
		return id;
	}
	
	public String getQuery(Map<String, String> paramMap) throws UnsupportedEncodingException {
		String str = "?";
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			str += String.format("%s=%s&", entry.getKey(), URLEncoder.encode(entry.getValue(), CHARSET));
		}
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	public void cleanDb() throws Exception {
		if (CLEAN_BEFORE_RUN_JUNIT_TESTS) {
			betResultDao.deleteAll();
			scorerDao.deleteAll();
			gameDao.deleteAll();
			tournamentPlayerDao.deleteAll();
			playerDao.deleteAll();
			tournamentTeamDao.deleteAll();
			teamDao.deleteAll();
			tournamentDao.deleteAll();
			betDao.deleteAll();
			statusDao.deleteAll();
    		userDao.deleteAll();
    		userRoleDao.deleteAll();
    	}
	}
	
	public UserRole addUserRole() throws Exception {
		String name = "ADMIN";
		UserRole userRole = new UserRole(name);
		return userRoleDao.save(userRole);
	}
	
	public void deleteUserRole(UserRole userRole) throws Exception {
		userRoleDao.deleteById(userRole.getId());
	}
	
	public User addUser() throws Exception {
		UserRole userRole = addUserRole();
		String email = "eemeli";
		String name = "nimi";
		String password = "salainensana";
		
		return userDao.save(new User(email, name, password, userRole));
	}
	
	public void deleteUser(User user) throws Exception {
		Long userRoleId = userDao.findById(user.getId()).get().getUserRole().getId();
		userDao.deleteById(Long.valueOf(user.getId().toString()));
		userRoleDao.deleteById(userRoleId);
	}
	
	public Status addStatus() throws Exception {
		String description = "statuksen kuvaus";
		return statusDao.save(new Status(STATUS_UNDER_WORK, description));
	}
	
	public void deleteStatus(Status status) throws Exception {
		statusDao.deleteById(status.getId());
	}
	
	public Bet addBet() throws Exception {
		User user = addUser();
		Tournament tournament = addTournament();
		Status status = addStatus();
		return betDao.save(new Bet(user, tournament, status));
	}
	
	public void deleteBet(Bet bet) throws Exception {
		betDao.deleteById(bet.getId());
	}
	
	public Tournament addTournament() throws Exception {
		String name = "Brazil World Cup";
		int year = 2014;
		return tournamentDao.save(new Tournament(name, year));
	}
	
	public void deleteTournament(Tournament tournament) throws Exception {
		tournamentDao.deleteById(tournament.getId());
	}
	
	public Team addTeam() throws Exception {
		String name = "KÄPA";
		return teamDao.save(new Team(name));
	}
	
	public void deleteTeam(Team team) throws Exception {
		teamDao.deleteById(team.getId());
	}
	
	public TournamentTeam addTournamentTeam() throws Exception {
		Tournament tournament = addTournament();
		Team team = addTeam();
		return tournamentTeamDao.save(new TournamentTeam(tournament, team));
	}
	
	public void deleteTournamentTeam(TournamentTeam tournamentTeam) throws Exception {
		tournamentTeamDao.deleteById(tournamentTeam.getId());
	}

	public Player addPlayer() throws Exception {
		String firstName = "Eric";
		String lastName = "Cantona";
		return playerDao.save(new Player(firstName, lastName));
	}
	
	public void deletePlayer(Player player) throws Exception {
		playerDao.deleteById(player.getId());
	}
	
	public TournamentPlayer addTournamentPlayer() throws Exception {
		TournamentTeam tournamentTeam = addTournamentTeam();
		Player player = addPlayer();
		int goals = 7;
		return tournamentPlayerDao.save(new TournamentPlayer(tournamentTeam, player, goals));
	}
	
	public void deleteTournamentPlayer(TournamentPlayer tournamentPlayer) throws Exception {
		tournamentPlayerDao.deleteById(tournamentPlayer.getId());
	}
	
	public Game addGame() throws Exception {
		int homeScore = 7;
		int awayScore = 3;
		Date gameDate = new Date();
		Tournament tournament = addTournament();
		TournamentTeam homeTeam = addTournamentTeam();
		TournamentTeam awayTeam = addTournamentTeam();
		awayTeam.getTeam().setName(awayTeam.getTeam().getName() + "Cameroon");
		
		return gameDao.save(new Game(tournament, homeTeam, awayTeam, homeScore, awayScore, gameDate));
	}
	
	public void deleteGame(Game game) throws Exception {
		gameDao.deleteById(game.getId());
	}
	
	public Scorer addScorer() throws Exception {
		TournamentPlayer tournamentPlayer = addTournamentPlayer();
		Game game = addGame();
		return scorerDao.save(new Scorer(tournamentPlayer, game));
	}
	
	public void deleteScorer(Scorer scorer) throws Exception {
		scorerDao.deleteById(scorer.getId());
	}
	
	public BetResult addBetResult() throws Exception {
		Bet bet = addBet();
		Game game = addGame();
		int homeScore = 7;
		int awayScore = 3;
		
		return betResultDao.save(new BetResult(bet, game, homeScore, awayScore));
	}
	
	public void deleteBetResult(BetResult betResult) throws Exception {
		betResultDao.deleteById(betResult.getId());
	}
	
}
