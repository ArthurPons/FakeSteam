package dao;

import java.util.List;

import bean.GameIsOfGenre;
import bean.Genre;

public interface GameIsOfGenreDao {

	  void create( GameIsOfGenre gameIsOfGenre ) throws DaoException;
	  void createSeveral (int idGame, List<Integer> listGenre) throws DaoException;
	  List<String> find( int id ) throws DaoException;
	  List<GameIsOfGenre> findAll () throws DaoException;
}
