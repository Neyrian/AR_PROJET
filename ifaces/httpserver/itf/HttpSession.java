package httpserver.itf;


/*
 * Provides a way to identify a user across more than one request and to store information about that user. 
 */
public interface HttpSession {
	public final String COOKIE_SESSION = "session-id";
	public final int EXPIRATION_TIME = 5000;

	/*
	 * Returns a string containing the unique identifier assigned to this session.
	 */
	public String getId();
	
	/*
	 * Returns the object bound with the specified name in this session, or null if no object is bound under the name.
	 */
	public Object getValue(String key);
	
	/*
	 * Binds an object to this session, using the name specified.
	 */
	public void setValue(String key, Object value) ;

}
