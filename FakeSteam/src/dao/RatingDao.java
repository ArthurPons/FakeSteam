package dao;

import bean.Rating;

public interface RatingDao {

	void create( Rating rating ) throws DaoException;
	Rating find( int id ) throws DaoException;
}
