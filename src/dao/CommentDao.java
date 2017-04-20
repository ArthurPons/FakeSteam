package dao;

import java.util.List;

import bean.Comment;

public interface CommentDao {

	  void create( Comment comment ) throws DaoException;
	  Comment find( int id ) throws DaoException;
	  List<Comment> findAll () throws DaoException;
}
