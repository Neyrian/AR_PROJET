package httpserver.itf;

/*
 * Extends the HttpResponse interface to provide response information for ricmlets. 
 */
public interface HttpRicmletResponse extends HttpResponse {

	/*
	 * Sets the value for the cookie of the given name
	 */
	abstract public void setCookie(String name, String value);



}
