package dao;

import java.util.List;

import bean.Game;
import bean.User;

public interface UserDao {

	void create( User user ) throws DaoException;
	User find( int id ) throws DaoException;
	List<User> findAll () throws DaoException;
	List<User> findByLogin(String username);
}
