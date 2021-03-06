package dao;

import java.util.List;

import javax.faces.bean.ManagedBean;

import bean.Comment;
import bean.Game;


public interface GameDao {
	 int create( Game game ) throws DaoException;
	 Game find( int id ) throws DaoException;
	 Game findByName (String gameName) throws DaoException;
	 List<Game> findAll () throws DaoException;
	 List<Game> findAllUserGame(int idUser) throws DaoException;

}
