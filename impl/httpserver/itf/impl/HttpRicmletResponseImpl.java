package httpserver.itf.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletResponseImpl implements HttpRicmletResponse {
	protected HttpServer m_hs;
	protected PrintStream m_ps;
	protected HttpRequest m_req;

	HashMap<String, String> m_cookies;

	public HttpRicmletResponseImpl(HttpServer hs, HttpRequest req, PrintStream ps) {
		m_hs = hs;
		m_req = req;
		m_ps = ps;
		m_cookies = new HashMap<String, String>();
	}

	@Override
	public void setReplyOk() throws IOException {
		m_ps.println("HTTP/1.0 200 OK");
		m_ps.println("Date: " + new Date());
		/*
		 * Get the session from the request and send it in the reply.
		 */
		if (m_req instanceof HttpRicmletRequestImpl) {
			String idSession = ((HttpRicmletRequestImpl) m_req).getCookie(HttpSession.COOKIE_SESSION);
			if (idSession != null)
				m_ps.println("Set-Cookie: " + HttpSession.COOKIE_SESSION + "=" + idSession);
		}
		
		/*
		 * Send the cookies
		 */
		
		for (String k : m_cookies.keySet())
			m_ps.println("Set-Cookie: " + k + "=" + m_cookies.get(k));

		m_ps.println("Server: ricm-http 1.0");
	}

	@Override
	public void setReplyError(int codeRet, String msg) throws IOException {
		m_ps.println("HTTP/1.0 " + codeRet + " " + msg);
		m_ps.println("Date: " + new Date());
		m_ps.println("Server: ricm-http 1.0");
		m_ps.println("Content-type: text/html");
		m_ps.println();
		m_ps.println("<HTML><HEAD><TITLE>" + msg + "</TITLE></HEAD>");
		m_ps.println("<BODY><H4>HTTP Error " + codeRet + ": " + msg + "</H4></BODY></HTML>");
		m_ps.flush();

	}

	@Override
	public void setContentLength(int length) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String type) throws IOException {
		m_ps.println("Content-type: " + type);
	}

	@Override
	public PrintStream beginBody() throws IOException {
		m_ps.println();
		return m_ps;
	}

	@Override
	public void setCookie(String name, String value) {
		m_cookies.put(name, value);
	}

}
