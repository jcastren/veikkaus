package fi.joonas.veikkaus.constants;

public abstract class VeikkausConstants {

    public static final String URL_GET_ALL = "/getAll";
    public static final String URL_GET_CREATE = "/getCreate";
    public static final String URL_GET_DELETE = "/getDelete";
    public static final String URL_GET_DETAILS = "/getDetails";
    public static final String URL_GET_MODIFY = "/getModify";
    public static final String URL_POST_CREATE = "/postCreate";
    public static final String URL_POST_DELETE = "/postDelete";
    public static final String URL_POST_MODIFY = "/postModify";

    public static final String REDIRECT = "redirect:";
    public static final String GET_DETAILS_ID_QUERY_PARAM = "?id=";

    public static final String USER_ROLE_URL = "/userRole";
    public static final String USER_ROLE_GET_ALL_URL = USER_ROLE_URL + URL_GET_ALL;
    public static final String ALL_USER_ROLES = "allUserRoles";

    public static final String USER_URL = "/user";
    public static final String USER_GET_ALL_URL = USER_URL + URL_GET_ALL;
    public static final String ALL_USERS = "allUsers";

    public static final String STATUS_URL = "/status";
    public static final String STATUS_GET_ALL_URL = STATUS_URL + URL_GET_ALL;
    public static final String ALL_STATUSES = "allStatuses";

    public static final String BET_URL = "/bet";
    public static final String BET_GET_ALL_URL = BET_URL + URL_GET_ALL;
    public static final String ALL_BETS = "allBets";
    public static final String BET_POST_BET_RESULT_SAVE = "postBetResultSave";
    public static final String BET_GET_DETAILS_URL = BET_URL + URL_GET_DETAILS + GET_DETAILS_ID_QUERY_PARAM;

    public static final String TOURNAMENT_URL = "/tournament";
    public static final String ALL_TOURNAMENTS = "allTournaments";

    public static final String TEAM_URL = "/team";

    public static final String TOURNAMENT_TEAM_URL = "/tournamentTeam";

    public static final String GAME_URL = "/game";
    public static final String ALL_GAMES = "allGames";

    public static final String BET_RESULT_URL = "/betResult";
    public static final String BET_RESULT_GET_ALL_URL = BET_RESULT_URL + URL_GET_ALL;

    public static final String PLAYER_URL = "/player";

    public static final String TOURNAMENT_PLAYER_URL = "/tournamentPlayer";
    public static final String ALL_TOURNAMENT_PLAYERS = "allTournamentPlayers";

    public static final String SCORER_URL = "/scorer";
    public static final String SCORER_GET_ALL_URL = SCORER_URL + URL_GET_ALL;

    public static final String PARAM_NAME_AWAY_SCORE = "awayScore";
    public static final String PARAM_NAME_AWAY_TEAM_ID = "awayTeamId";
    public static final String PARAM_NAME_BET_ID = "betId";
    public static final String PARAM_NAME_DESCRIPTION = "description";
    public static final String PARAM_NAME_EMAIL = "email";
    public static final String PARAM_NAME_FIRST_NAME = "firstName";
    public static final String PARAM_NAME_GAME_DATE = "gameDate";
    public static final String PARAM_NAME_GAME_ID = "gameId";
    public static final String PARAM_NAME_GOALS = "goals";
    public static final String PARAM_NAME_HOME_SCORE = "homeScore";
    public static final String PARAM_NAME_HOME_TEAM_ID = "homeTeamId";
    public static final String PARAM_NAME_ID = "id";
    public static final String PARAM_NAME_LAST_NAME = "lastName";
    public static final String PARAM_NAME_NAME = "name";
    public static final String PARAM_NAME_PASSWORD = "password";
    public static final String PARAM_NAME_PLAYER_ID = "playerId";
    public static final String PARAM_NAME_STATUS_NUMBER = "statusNumber";
    public static final String PARAM_NAME_STATUS_ID = "statusId";
    public static final String PARAM_NAME_TEAM_ID = "teamId";
    public static final String PARAM_NAME_TOURNAMENT_ID = "tournamentId";
    public static final String PARAM_NAME_TOURNAMENT_PLAYER_ID = "tournamentPlayerId";
    public static final String PARAM_NAME_TOURNAMENT_TEAM_ID = "tournamentTeamId";
    public static final String PARAM_NAME_USER_ID = "userId";
    public static final String PARAM_NAME_USER_ROLE_ID = "userRoleId";
    public static final String PARAM_NAME_YEAR = "year";

    public static final int STATUS_UNDER_WORK = 1;
    public static final int STATUS_COMPLETED = 2;

    public static final String URL_CREATE = "/getCreate";
    public static final String URL_DELETE = "/getDelete";
    public static final String URL_MODIFY = "/getModify";

    public static final String USER_ROLE_CREATE_URL = USER_ROLE_URL + URL_CREATE;
    public static final String USER_ROLE_DELETE_URL = USER_ROLE_URL + URL_DELETE;
    public static final String USER_ROLE_MODIFY_URL = USER_ROLE_URL + URL_MODIFY;

    public static final String USER_CREATE_URL = USER_URL + URL_CREATE;
    public static final String USER_DELETE_URL = USER_URL + URL_DELETE;
    public static final String USER_MODIFY_URL = USER_URL + URL_MODIFY;

    public static final String STATUS_CREATE_URL = STATUS_URL + URL_CREATE;
    public static final String STATUS_DELETE_URL = STATUS_URL + URL_DELETE;
    public static final String STATUS_MODIFY_URL = STATUS_URL + URL_MODIFY;

    public static final String BET_CREATE_URL = BET_URL + URL_CREATE;
    public static final String BET_DELETE_URL = BET_URL + URL_DELETE;
    public static final String BET_MODIFY_URL = BET_URL + URL_MODIFY;

    public static final String TEAM_CREATE_URL = TEAM_URL + URL_CREATE;
    public static final String TEAM_DELETE_URL = TEAM_URL + URL_DELETE;
    public static final String TEAM_MODIFY_URL = TEAM_URL + URL_MODIFY;

    public static final String TOURNAMENT_TEAM_CREATE_URL = TOURNAMENT_TEAM_URL + URL_CREATE;
    public static final String TOURNAMENT_TEAM_DELETE_URL = TOURNAMENT_TEAM_URL + URL_DELETE;
    public static final String TOURNAMENT_TEAM_MODIFY_URL = TOURNAMENT_TEAM_URL + URL_MODIFY;

    public static final String GAME_CREATE_URL = GAME_URL + URL_CREATE;
    public static final String GAME_DELETE_URL = GAME_URL + URL_DELETE;
    public static final String GAME_MODIFY_URL = GAME_URL + URL_MODIFY;

    public static final String BET_RESULT_CREATE_URL = BET_RESULT_URL + URL_CREATE;
    public static final String BET_RESULT_DELETE_URL = BET_RESULT_URL + URL_DELETE;
    public static final String BET_RESULT_MODIFY_URL = BET_RESULT_URL + URL_MODIFY;

    public static final String PLAYER_CREATE_URL = PLAYER_URL + URL_CREATE;
    public static final String PLAYER_DELETE_URL = PLAYER_URL + URL_DELETE;
    public static final String PLAYER_MODIFY_URL = PLAYER_URL + URL_MODIFY;

    public static final String TOURNAMENT_PLAYER_CREATE_URL = TOURNAMENT_PLAYER_URL + URL_CREATE;
    public static final String TOURNAMENT_PLAYER_DELETE_URL = TOURNAMENT_PLAYER_URL + URL_DELETE;
    public static final String TOURNAMENT_PLAYER_MODIFY_URL = TOURNAMENT_PLAYER_URL + URL_MODIFY;

    public static final String TOURNAMENT_CREATE_URL = TOURNAMENT_URL + URL_CREATE;
    public static final String TOURNAMENT_DELETE_URL = TOURNAMENT_URL + URL_DELETE;
    public static final String TOURNAMENT_MODIFY_URL = TOURNAMENT_URL + URL_MODIFY;

    public static final String SCORER_CREATE_URL = SCORER_URL + URL_CREATE;
    public static final String SCORER_DELETE_URL = SCORER_URL + URL_DELETE;
    public static final String SCORER_MODIFY_URL = SCORER_URL + URL_MODIFY;

    public static final int INT_NOT_DEFINED = -999;
    public static final long LONG_NOT_DEFINED = -999L;
    public static final String STRING_NOT_DEFINED = "-999";
}