package dao;

import java.util.List;

import bean.Genre;

public interface GenreDao {

	void create( Genre genre ) throws DaoException;
	Genre find( int id ) throws DaoException;
	List<Genre> findAll () throws DaoException;
}
