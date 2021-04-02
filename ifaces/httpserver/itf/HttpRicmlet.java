package httpserver.itf;

import java.io.IOException;

import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;



public interface HttpRicmlet {
	
	/*
	 * Called by the server to allow a ricmlet to handle a GET request. 
	 * When overriding this method, read the request data, write the response headers, 
	 * get the response's output stream, and finally, write the response data. 
	 * @param req: an HttpRicmletRequest object that contains the request the client has made of the ricmlet
	 * @param resp: an HttpRicmletResponse object that contains the response the ricmlet sends to the client
	 * @throws IOException if an input or output error is detected when the ricmlet handles the GET request
	 */
	public void doGet(HttpRicmletRequest req, HttpRicmletResponse resp) throws IOException ;
}
