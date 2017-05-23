package dao;

import java.util.List;

import bean.UserOwnsGame;

public interface UserOwnsGameDao {

	  void create( UserOwnsGame userOwnsGame ) throws DaoException;
	  UserOwnsGame find( int id ) throws DaoException;
	  List<UserOwnsGame> findAll () throws DaoException;
}
