package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

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
	String m_ricmlet;

	/*
	 * Contains the keys of the request and their values
	 */
	HashMap<String, String> m_args;

	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
		super(hs, method, ressname, br);
		m_args = new HashMap<String, String>();
		m_br = br;

		/*
		 * Splits ressname to separate the path and the arguments
		 */
		String[] tmp = ressname.split("\\?");

		/*
		 * Changing the path to the class name in order to instantiate it as a class
		 */
		m_ricmlet = tmp[0].replaceAll("/", ".");
		m_ricmlet = m_ricmlet.substring("/ricmlets/".length());

		if (tmp.length >= 2)
			for (String arg : tmp[1].split("&")) {
				String[] key_val = arg.split("=");
				m_args.put(key_val[0], key_val[1]);
			}
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {
		return m_args.get(name);
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		try {
			/*
			 * Instantiating the Ricmlet
			 */
			Class<?> c = Class.forName(m_ricmlet);
			((HttpRicmlet) c.getDeclaredConstructor().newInstance()).doGet(this, (HttpRicmletResponse) resp);
		} catch (ClassNotFoundException e) {
			resp.setReplyError(404, "Ricmlet Not Found");
		}
	}

}
