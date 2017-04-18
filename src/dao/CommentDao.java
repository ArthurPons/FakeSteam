package dao;

import bean.Comment;

public interface CommentDao {

	  void create( Comment comment ) throws DaoException;
	  Comment find( int id ) throws DaoException;
}
