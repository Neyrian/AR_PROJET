package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {
	BufferedReader m_br;

	/*
	 * Contains the path
	 */
	String m_clsname;

	/*
	 * Contains the keys of the request and their values
	 */
	HashMap<String, String> m_args;

	HashMap<String, String> m_cookies;
	
	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
		super(hs, method, ressname, br);
		m_args = new HashMap<String, String>();
		m_cookies = new HashMap<String, String>();

		m_br = br;

		/*
		 * Parsing Cookie
		 */
		String line;
		StringTokenizer parse = null;
		String name = null;
		/*
		 * Searching for the line containing the cookies in the request header
		 */
		do {
			line = br.readLine();
			if (line.equals(""))
				break;
			parse = new StringTokenizer(line);
			name = parse.nextToken();
			switch (name) {
			case "Cookie:":
				parseCookies(line);
				break;
			default:
				break;
			}
		} while (name != null);

		/*
		 * Splits ressname to separate the path and the arguments
		 */
		String[] tmp = ressname.split("\\?");

		/*
		 * Changing the path to the class name in order to get the class name.
		 */
		m_clsname = tmp[0].replaceAll("/", ".");
		m_clsname = m_clsname.substring(".ricmlets.".length());

		if (tmp.length >= 2)
			for (String arg : tmp[1].split("&")) {
				String[] key_val = arg.split("=");
				m_args.put(key_val[0], key_val[1]);
			}
	}
		
	private void parseCookies(String line) {
		/*
		 * Parsing all the cookies if there is a line corresponding to the cookies in
		 * the request
		 */
		StringTokenizer parse = new StringTokenizer(line);
		parse.nextToken();
		String NextCookie;
		while (parse.countTokens() > 0) {
			NextCookie = parse.nextToken();
			NextCookie.replaceAll(";", "");
			String[] key_val_cookie = NextCookie.split("=");
			m_cookies.put(key_val_cookie[0], key_val_cookie[1]);
		}

	}

	@Override
	public HttpSession getSession() {
		String Id = m_cookies.get(HttpSession.COOKIE_SESSION);
		if (Id == null) {
			m_cookies.put(HttpSession.COOKIE_SESSION, m_hs.getNextSession());
		}
		return m_hs.getSession(Id);
	}

	@Override
	public String getArg(String name) {
		return m_args.get(name);
	}

	@Override
	public String getCookie(String name) {
		return m_cookies.get(name);
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		try {
			/*
			 * Get the instance of the ricmlet from the server
			 */
			HttpRicmlet ricmlet = m_hs.getInstance(m_clsname);
			
			ricmlet.doGet(this, (HttpRicmletResponse) resp);

		} catch (ClassNotFoundException e) {
			resp.setReplyError(404, "Ricmlet Not Found");
		}
	}

}
