package dao;

import java.util.List;

import bean.Console;

public interface ConsoleDao {

	  void create( Console console ) throws DaoException;
	  Console find( int id ) throws DaoException;
	  List<Console> findAll () throws DaoException;
}
