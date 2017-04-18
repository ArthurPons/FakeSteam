package dao;

import java.util.List;

import javax.faces.bean.ManagedBean;

import bean.Comment;
import bean.Game;


public interface GameDao {
	 void create( Game game ) throws DaoException;
	 Game find( int id ) throws DaoException;
	 List<Game> findAll () throws DaoException;

}
