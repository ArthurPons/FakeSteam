package dao;

import java.util.List;

import bean.GameIsOfGenre;

public interface GameIsOfGenreDao {

	  void create( GameIsOfGenre gameIsOfGenre ) throws DaoException;
	  GameIsOfGenre find( int id ) throws DaoException;
	  List<GameIsOfGenre> findAll () throws DaoException;
}
