package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

/*
 * Worker class
 */
public class HttpWorker extends Thread {
	HttpServer m_hs;
	Socket m_soc;

	HttpWorker(HttpServer hs, Socket soc) {
		m_hs = hs;
		m_soc = soc;
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(m_soc.getInputStream()));
			PrintStream ps = new PrintStream(m_soc.getOutputStream());
			HttpRequest req = m_hs.getRequest(br);
			HttpResponse resp = m_hs.getResponse(req, ps);
			req.process(resp);
		} catch (Exception e) {
			System.err.println("Server exception, skipping to next request " + e);		
		} finally {
			try {
				m_soc.close();
			} catch (Exception e) {
				System.err.println("Error closing stream : " + e);
			} 
		}
	}

}