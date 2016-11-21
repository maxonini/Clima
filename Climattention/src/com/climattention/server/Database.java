package com.climattention.server;

import com.climattention.shared.DataQuery;
import com.climattention.shared.DataQueryResult;

/**
 * Abstraction of the movies database.
 */
public interface Database {

	/**
	 * Returns the movies that match the given query.
	 * 
	 * @param query
	 *            the query containing the search criteria
	 * @return the matching movies
	 */
	DataQueryResult query(DataQuery query);
}
