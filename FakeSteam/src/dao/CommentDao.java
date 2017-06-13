package dao;

import java.util.List;

import bean.Comment;
import bean.User;

public interface CommentDao {

	  void create( User user ) throws DaoException;
	  Comment find( int id ) throws DaoException;
	  List<Comment> findAll () throws DaoException;
	  List<Comment> findGameComments(int gameId) throws DaoException;
}
