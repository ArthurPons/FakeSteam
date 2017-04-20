package dao;

import bean.Historic;

public interface HistoricDao {
	void create( Historic historic ) throws DaoException;
	Historic find( int id ) throws DaoException;
}
