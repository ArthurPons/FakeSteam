package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DaoTools.*;

import bean.Game;
import bean.Historic;
import bean.User;

public class HistoricDaoImpl implements HistoricDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM historic WHERE id_historic = ?";
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
	public void create( Historic historic ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, historic.getGameId(), historic.getUserId(), historic.getDateHistoric() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de l'ajout d'une ligne dans la table historic, aucune ligne ajoutée." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            historic.setIdHistoric((int) valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "Échec de la création de l'historique en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public Historic find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Historic historic = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         historic = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return historic;
	}
	
	
	private static Historic map( ResultSet resultSet ) throws SQLException {
	    Historic historic = new Historic();
	    historic.setIdHistoric(resultSet.getInt( "id_historic" ) );
	    historic.setDateHistoric( resultSet.getDate("date_historic") );
	   	   
	    
	    //recherche de l'objet game (id puis objet) 
	    int gameId = resultSet.getInt("fk_game");	    
	   
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    historic.setGame(g.find(gameId));

	    //recherche du user (id puis objet) 
	    int userId=resultSet.getInt("fk_user");
	    UserDaoImpl u = new UserDaoImpl(daoFactory);
	    historic.setUser(u.find(userId));
	    
	   	 
	    return historic;
	}
	
}
