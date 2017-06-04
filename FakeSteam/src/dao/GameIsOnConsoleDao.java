package dao;

import java.util.List;

import bean.GameIsOnConsole;

public interface GameIsOnConsoleDao {

	  void create( GameIsOnConsole gameIsOnConsole ) throws DaoException;
	  List<String> find( int id ) throws DaoException;
	  List<GameIsOnConsole> findAll () throws DaoException;
	  void createSeveral(int idGame, List<Integer> listConsoles) throws DaoException;
	
}
