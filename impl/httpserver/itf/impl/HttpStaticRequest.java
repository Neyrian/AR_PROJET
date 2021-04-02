package httpserver.itf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

public class HttpStaticRequest extends HttpRequest {
	static final String DEFAULT_FILE = "index.html";

	public HttpStaticRequest(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}

	public void process(HttpResponse resp) throws Exception {
		/*
		 * Get the path of the requeted file
		 */
		String path = (m_hs.getFolder().getPath() + m_ressname);
		/*
		 * Try to open the file requested and check if it exists.
		 */
		File f = new File(path);
		
		/*
		 * Checking if the requested path is a directory
		 * If it is, send the DEFAULT_FILE contained in the directory
		 * To do so, we change the variable path and open the index.html
		 */
		
		if (f.isDirectory()) {
			path = (f.getPath() + "/" + DEFAULT_FILE);
			f = new File(path);
		}
		
		if (f.isFile()) {
			
			FileInputStream fs = new FileInputStream(f);
			/*
			 * Writing response header
			 */
			resp.setReplyOk();

			/*
			 * Get the size of the file
			 */

			int size = (int) f.length();

			resp.setContentLength(size);
			resp.setContentType(getContentType(path));
			PrintStream bodyStream = resp.beginBody();
			/*
			 * End of the header 
			 * Writing file content Reading charater by character
			 */
			for (int i = 0; i < size; i++)
				bodyStream.print((char) fs.read());

			fs.close();
			
		} else // File not found
			resp.setReplyError(404, "File not found");

	}

}
