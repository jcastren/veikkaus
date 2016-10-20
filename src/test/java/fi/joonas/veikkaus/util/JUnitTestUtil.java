package fi.joonas.veikkaus.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;

public abstract class JUnitTestUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JUnitTestUtil.class);
	
	public static final boolean CLEAN_BEFORE_RUN_JUNIT_TESTS = true;
	public static final String CHARSET = java.nio.charset.StandardCharsets.UTF_8.name();
	
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserDao userDao;
	//@Autowired BetDao betDao;
	
	/*
	tournamentDao.deleteAll();
	teamDao.deleteAll();
	tournamentTeamDao.deleteAll();
	playerDao.deleteAll();
	tournamentPlayerDao.deleteAll();
	scorerDao.deleteAll();
	gameDao.deleteAll();
	betResultDao.deleteAll();
	betDao.deleteAll();*/
	
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

	/*
	 * private String getQuery(String...strings) { String query = null; if
	 * (strings != null) {
	 * 
	 * String formattedStr;
	 * 
	 * for (String str : strings) {
	 * 
	 * }
	 * 
	 * query = String.format("email=%s&name=%s&password=%s&userRoleId=%s",
	 * URLEncoder.encode(email, charset), URLEncoder.encode(name, charset),
	 * URLEncoder.encode(password, charset), URLEncoder.encode(userRoleId,
	 * charset)); } return query; }
	 */

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

	public static String getEncodedStr(String str) throws UnsupportedEncodingException {
		String ret = URLEncoder.encode(str, CHARSET);
		return ret;
	}
	
	public void cleanDb() throws Exception {
		if (CLEAN_BEFORE_RUN_JUNIT_TESTS) {
			/*
			tournamentDao.deleteAll();
			teamDao.deleteAll();
			tournamentTeamDao.deleteAll();
			playerDao.deleteAll();
			tournamentPlayerDao.deleteAll();
			scorerDao.deleteAll();
			gameDao.deleteAll();
			betResultDao.deleteAll();
			betDao.deleteAll();
			*/
    		userDao.deleteAll();
    		userRoleDao.deleteAll();
    	}
	}
	
	public String addUserRole() throws Exception {
		String roleName = "ADMIN";
		UserRole userRole = new UserRole(roleName);
		return userRoleDao.save(userRole).getId().toString();
	}
	
	public void deleteUserRole(String userRoleId) throws Exception {
		userRoleDao.delete(Long.valueOf(userRoleId));
	}
	
	public String addUser() throws Exception {
		UserRole userRole = userRoleDao.findOne(Long.valueOf(addUserRole()));
		String email = "eemeli";
		String name = "nimi";
		String password = "salainensana";
		
		return userDao.save(new User(email, name, password, userRole)).getId().toString();
	}
	
	public void deleteUser(String userId) throws Exception {
		Long userRoleId = userDao.findOne(Long.valueOf(userId)).getRole().getId();
		userDao.delete(Long.valueOf(userId));
		userRoleDao.delete(userRoleId);
	}
	
}
