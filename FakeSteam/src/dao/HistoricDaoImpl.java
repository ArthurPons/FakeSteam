package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoTools.*;

import bean.Game;
import bean.Historic;
import bean.User;

public class HistoricDaoImpl implements HistoricDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM historic WHERE fk_user = ?";
	private static final String SQL_INSERT = "INSERT INTO historic (fk_game, fk_user, date_historic) VALUES (?, ?, ?)";
	
	public HistoricDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public HistoricDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( User user ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    
	    java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        
	        //ajoute tous les jeux du panier dans l'historique (avec la date courante)
	        for(Game g : user.getPanier())
	        {	            
	        	
		        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, g.getIdGame(),user.getIdUser(), sqlDate );
		        int statut = preparedStatement.executeUpdate();
		        /* Analyse du statut retourné par la requête d'insertion */
		        if ( statut == 0 ) {
		            throw new DaoException( "Échec de l'ajout d'une ligne dans la table historic, aucune ligne ajoutée." );
		        }
		      
	        }
	   
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public List<Historic> find( int userId ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Historic historic = null;
		 List<Historic> listOfHistory = new ArrayList<>();
		 
		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, userId );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         historic = map( resultSet );
		         listOfHistory.add(historic);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfHistory;
	}
	
	
	private static Historic map( ResultSet resultSet ) throws SQLException {
	    Historic historic = new Historic();
	    historic.setIdHistoric(resultSet.getInt( "id_historic" ) );
	    historic.setDateHistoric( resultSet.getDate("date_historic") );
	   	 historic.setFk_game(resultSet.getInt("fk_game"));
	   	 
	    return historic;
	}
	
}
