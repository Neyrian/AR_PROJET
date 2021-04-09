package httpserver.itf.impl;

import java.util.HashMap;

import httpserver.itf.HttpSession;

public class HttpSessionImpl implements HttpSession{
	private HashMap<String, Object> m_set;
	private String m_Id;
	
	
	
	public  HttpSessionImpl(String Id) {
		m_set = new HashMap<String, Object>();
		m_Id = Id;
	}
	
	@Override
	public String getId() {
		return m_Id;
	}

	@Override
	public Object getValue(String key) {
		return m_set.get(key);
	}

	@Override
	public void setValue(String key, Object value) {
		m_set.put(key, value);
	}

}
