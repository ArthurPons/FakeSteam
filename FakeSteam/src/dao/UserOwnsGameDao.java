package dao;

import java.util.List;

import bean.User;
import bean.UserOwnsGame;

public interface UserOwnsGameDao {

	  void create( User user ) throws DaoException;
	  UserOwnsGame find( int id ) throws DaoException;
	  List<UserOwnsGame> findAll () throws DaoException;
}
