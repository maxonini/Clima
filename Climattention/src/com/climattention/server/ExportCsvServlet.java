package com.climattention.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.climattention.shared.DataQuery;
import com.climattention.shared.Datapoint;
import com.google.gwt.thirdparty.guava.common.base.Joiner;


public class ExportCsvServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Database database = DatabaseFactory.getInstance();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		DataQuery query = new DataQuery();
		//query.setName(request.getParameter("name"));
		//query.setYear(request.getParameter("year"));
		//query.setCountry(request.getParameter("country"));
		//query.setLanguage(request.getParameter("language"));
		//query.setGenre(request.getParameter("genre"));
		//query.setAscending(true);
		//query.setSortColumn(SortColumn.TITLE);

		List<Datapoint> exportList = database.query(query).getData();

		if (exportList.size() == 0) {
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('No movies to export');");
			out.println("</script>");

			return;

		}
		response.setContentType("text/csv; charset=utf-8");

		response.setHeader("Content-Disposition", "attachment; fileName="
				+ "MovieExportList" + ".csv");

		Joiner listJoiner = Joiner.on('/');

		for (Datapoint data: exportList) {
			out.println(String.valueOf(data.getTemperature()) + ","
					+ data.getCity() + ","
					+ data.getCountry() + ","
					+ data.getYear() + ","
					+ data.getLatitude()+ ","
					+ data.getLongitude()+ ","
					//+ listJoiner.join(data.getLanguages()) + ","
					//+ listJoiner.join(data.getCountries()) + ","
					//+ listJoiner.join(data.getGenres())
					);
		}

		out.close();
	}
}