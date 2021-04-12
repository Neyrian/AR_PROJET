package httpserver.itf.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Timer;

import httpserver.itf.HttpSession;

public class HttpSessionImpl implements HttpSession{
	
	private HashMap<String, Object> m_set;
	private String m_Id;
	private Timer m_timer;
	
	public  HttpSessionImpl(String Id, HttpServer hs) {
		m_set = new HashMap<String, Object>();
		m_Id = Id;
		m_timer = new Timer(EXPIRATION_TIME, new SessionListener(hs, this));
		m_timer.start();
	}
	
	@Override
	public String getId() {
		m_timer.restart();
		return m_Id;
	}

	@Override
	public Object getValue(String key) {
		m_timer.restart();
		return m_set.get(key);
	}

	@Override
	public void setValue(String key, Object value) {
		m_timer.restart();
		m_set.put(key, value);
	}
	
	public Timer getTimer() {
		return m_timer;
	}
}

class SessionListener implements ActionListener{
	HttpServer m_hs;
	HttpSession m_session;

	public SessionListener(HttpServer hs, HttpSession session) {
		m_hs = hs;
		m_session = session;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		m_hs.deleteSession(m_session.getId());
		
	}
	
	
}
