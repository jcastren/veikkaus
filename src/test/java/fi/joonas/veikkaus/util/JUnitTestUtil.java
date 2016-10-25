package fi.joonas.veikkaus.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fi.joonas.veikkaus.dao.BetDao;
import fi.joonas.veikkaus.dao.BetResultDao;
import fi.joonas.veikkaus.dao.GameDao;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.dao.ScorerDao;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.dao.TournamentDao;
import fi.joonas.veikkaus.dao.TournamentPlayerDao;
import fi.joonas.veikkaus.dao.TournamentTeamDao;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.Bet;
import fi.joonas.veikkaus.jpaentity.Player;
import fi.joonas.veikkaus.jpaentity.Status;
import fi.joonas.veikkaus.jpaentity.Tournament;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;

import static fi.joonas.veikkaus.constants.VeikkausConstants.*;

public abstract class JUnitTestUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JUnitTestUtil.class);
	
	public static final boolean CLEAN_BEFORE_RUN_JUNIT_TESTS = true;
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
	
	/**
	 * 
	 * @param url URL called
	 * @param getId If true, id is parsed from response and returned
	 * @return Id if getId == true. Empty value if getId == false.
	 * @throws Exception
	 */
	public static String callUrl(String url, boolean getId) throws Exception {
		String id = "";

		InputStream response;
		try {
			response = new URL(url).openStream();
		} catch (IOException e) {
			logger.error("Getting response from URL: " + url + " failed: ", e);
			throw e;
		}

		String str = null;
		try (Scanner scanner = new Scanner(response)) {
			String responseBody = scanner.useDelimiter("\\A").next();
			logger.info("URL: " + url + " produced responseBody: " + responseBody);
			str = responseBody;
		}

		if (getId) {
			Pattern p = Pattern.compile("(\\d+)");
			Matcher matcher = p.matcher(str);

			if (matcher.find()) {
				logger.info("id found: " + matcher.group(1));
				id = matcher.group(1);
			}
		}
		return id;
	}

	@Deprecated
	public static String getFormattedStr(int val) {
		String str = "";
		for (int i = 1; i <= val; i++) {
			if (i > 1) {
				str = str + "&";
			}
			str = str + "%s=%s";
		}
		return str;
	}

	@Deprecated
	public static String getEncodedStr(String str) throws UnsupportedEncodingException {
		String ret = URLEncoder.encode(str, CHARSET);
		return ret;
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
	
	public void deleteUserRole(String userRoleId) throws Exception {
		userRoleDao.delete(Long.valueOf(userRoleId));
	}
	
	public User addUser() throws Exception {
		UserRole userRole = addUserRole();
		String email = "eemeli";
		String name = "nimi";
		String password = "salainensana";
		
		return userDao.save(new User(email, name, password, userRole));
	}
	
	public void deleteUser(String userId) throws Exception {
		Long userRoleId = userDao.findOne(Long.valueOf(userId)).getRole().getId();
		userDao.delete(Long.valueOf(userId));
		userRoleDao.delete(userRoleId);
	}
	
	public Status addStatus() throws Exception {
		String description = "statuksen kuvaus";
		return statusDao.save(new Status(STATUS_UNDER_WORK, description));
	}
	
	public void deleteStatus(String statusId) throws Exception {
		statusDao.delete(Long.valueOf(statusId));
	}
	
	public Bet addBet() throws Exception {
		Status status = addStatus();
		User user = addUser();
		return betDao.save(new Bet(user, status));
	}
	
	public void deleteBet(String betId) throws Exception {
		betDao.delete(Long.valueOf(betId));
	}
	
	public Tournament addTournament() throws Exception {
		String name = "Brazil World Cup";
		int year = 2014;
		return tournamentDao.save(new Tournament(name, year));
	}
	
	public void deleteTournament(String tournamentId) throws Exception {
		tournamentDao.delete(Long.valueOf(tournamentId));
	}
	
	public Player addPlayer() throws Exception {
		String firstName = "Eric";
		String lastName = "Cantona";
		return playerDao.save(new Player(firstName, lastName));
	}
	
	public void deletePlayer(String playerId) throws Exception {
		playerDao.delete(Long.valueOf(playerId));
	}
	
}
