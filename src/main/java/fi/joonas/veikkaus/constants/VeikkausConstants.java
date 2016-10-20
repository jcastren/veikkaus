package fi.joonas.veikkaus.constants;

public abstract class VeikkausConstants {

	/** URL Handling definitions start */
	
	/** BASE URLs */
    public static final String URL_CREATE = "/create";
    public static final String URL_DELETE = "/delete";
    public static final String URL_MODIFY = "/modify";
    public static final String URL_GET_BY_EMAIL = "/get-by-email";
    
    public static final String SERVER_URL = "http://localhost:8080/veikkaus";
   
    /** UserRole URLs*/
    public static final String USER_ROLE = "/userrole";
    public static final String USER_ROLE_URL = SERVER_URL + USER_ROLE;
    public static final String USER_ROLE_CREATE_URL = USER_ROLE_URL + URL_CREATE;
    public static final String USER_ROLE_DELETE_URL = USER_ROLE_URL + URL_DELETE;
    
    /** User URLs */
    public static final String USER = "/user";
    public static final String USER_URL = SERVER_URL + USER;
    public static final String USER_CREATE_URL = USER_URL + URL_CREATE;
    public static final String USER_DELETE_URL = USER_URL + URL_DELETE;
    public static final String USER_MODIFY_URL = USER_URL + URL_MODIFY;
    
    /** Status URLs */
    public static final String STATUS = "/status";
    public static final String STATUS_URL = SERVER_URL + STATUS;
    public static final String STATUS_CREATE_URL = STATUS_URL + URL_CREATE;
    public static final String STATUS_DELETE_URL = STATUS_URL + URL_DELETE;
    public static final String STATUS_MODIFY_URL = STATUS_URL + URL_MODIFY;
    
    /** Bet URLs */
    public static final String BET = "/bet";
    public static final String BET_URL = SERVER_URL + "/bet";
    public static final String BET_CREATE_URL = BET_URL + URL_CREATE;
    public static final String BET_DELETE_URL = BET_URL + URL_DELETE;
    public static final String BET_MODIFY_URL = BET_URL + URL_MODIFY;
    
    /** URL parameters */
    public static final String PARAM_NAME_DESCRIPTION = "description";
    public static final String PARAM_NAME_EMAIL = "email";
    public static final String PARAM_NAME_ID = "id";
    public static final String PARAM_NAME_NAME = "name";
    public static final String PARAM_NAME_PASSWORD = "password";
    public static final String PARAM_NAME_ROLENAME = "roleName";
    public static final String PARAM_NAME_STATUS_NUMBER = "statusNumber";
    public static final String PARAM_NAME_STATUS_ID = "statusId";
    public static final String PARAM_NAME_USER_ID = "userId";
    public static final String PARAM_NAME_USER_ROLE_ID = "userRoleId";

    /** URL Handling definitions end */
    
    /** Application parameter values */
    public static final int STATUS_UNDER_WORK = 1;
    public static final int STATUS_COMPLETED = 2;

}
