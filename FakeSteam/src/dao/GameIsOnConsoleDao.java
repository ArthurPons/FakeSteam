package dao;

import java.util.List;

import bean.GameIsOnConsole;

public interface GameIsOnConsoleDao {

	  void create( GameIsOnConsole gameIsOnConsole ) throws DaoException;
	  GameIsOnConsole find( int id ) throws DaoException;
	  List<GameIsOnConsole> findAll () throws DaoException;
}
