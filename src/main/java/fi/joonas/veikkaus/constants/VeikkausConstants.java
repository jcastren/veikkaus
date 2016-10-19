package fi.joonas.veikkaus.constants;

public abstract class VeikkausConstants {

	/** URL Handling definitions start */
	
	/** BASE URLs */
    public static final String URL_CREATE = "/create";
    public static final String URL_DELETE = "/delete";
    public static final String URL_MODIFY = "/modify";
    
    public static final String SERVER_URL = "http://localhost:8080/veikkaus";
   
    /** UserRole URLs*/
    public static final String USER_ROLE_URL = SERVER_URL + "/userrole";
    public static final String USER_ROLE_CREATE_URL = USER_ROLE_URL + URL_CREATE;
    public static final String USER_ROLE_DELETE_URL = USER_ROLE_URL + URL_DELETE;
    
    /** User URLs */
    public static final String USER_URL = SERVER_URL + "/user";
    public static final String USER_CREATE_URL = USER_URL + URL_CREATE;
    public static final String USER_DELETE_URL = USER_URL + URL_DELETE;
    public static final String USER_MODIFY_URL = USER_URL + URL_MODIFY;
    
    /** URL parameters */
    public static final String PARAM_NAME_EMAIL = "email";
    public static final String PARAM_NAME_ID = "id";
    public static final String PARAM_NAME_NAME = "name";
    public static final String PARAM_NAME_PASSWORD = "password";
    public static final String PARAM_NAME_ROLENAME = "roleName";
    public static final String PARAM_NAME_USER_ROLE_ID = "userRoleId";

    /** URL Handling definitions end */

}
