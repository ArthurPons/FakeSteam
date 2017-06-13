package dao;

import java.util.List;

import bean.Historic;
import bean.User;

public interface HistoricDao {
	void create( User user ) throws DaoException;
	List<Historic> find( int id ) throws DaoException;
}
