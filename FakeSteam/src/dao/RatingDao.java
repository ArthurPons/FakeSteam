package dao;

import java.util.List;

import bean.Rating;
import bean.User;

public interface RatingDao {

	void create( User user ) throws DaoException;
	Rating find( int id ) throws DaoException;
	int findUserRating (int userId, int gameId) throws DaoException;
	List<Integer> findGameRating (int gameId) throws DaoException;
}
