package com.climattention.server;

import com.climattention.client.GreetingService;
import com.climattention.shared.DataQuery;
import com.climattention.shared.DataQueryResult;
import com.climattention.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	
	// use interface in declaration so that you can change the implementation
		private Database database = DatabaseFactory.getInstance();

		public DataQueryResult getDataFromServer(DataQuery query) {
			// Escape data from the client to avoid cross-site script
			// vulnerabilities.
			// query = escapeHtml(query);

			return database.query(query);
		}

		/**
		 * Escape an html string. Escaping data received from the client helps to
		 * prevent cross-site script vulnerabilities.
		 * 
		 * @param html
		 *            the html string to escape
		 * @return the escaped string
		 */
		private String escapeHtml(String html) {
			if (html == null) {
				return null;
			}
			return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
		}



	
	
	
}
