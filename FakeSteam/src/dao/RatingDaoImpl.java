package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DaoTools.*;

import bean.Game;
import bean.Rating;
import bean.User;

public class RatingDaoImpl implements RatingDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM rating WHERE id_rating = ?";
	private static final String SQL_INSERT = "INSERT INTO rating (fk_game_rating, fk_user_rating, rating_rating) VALUES (?, ?, ?)";
	
	public RatingDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public RatingDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( Rating rating ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, rating.getGameId(), rating.getUserId(),rating.getRatingRating() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de l'ajout d'une ligne dans la table historic, aucune ligne ajoutée." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            rating.setIdRating((int) valeursAutoGenerees.getLong( 1 ) );
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
	public Rating find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Rating rating = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         rating = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return rating;
	}
	
	
	private static Rating map( ResultSet resultSet ) throws SQLException {
	    Rating rating = new Rating();
	    rating.setIdRating(resultSet.getInt( "id_rating" ) );
	    rating.setRatingRating( resultSet.getInt("rating_rating") );
	   	   
	    
	    //recherche de l'objet game (id puis objet) 
	    int gameId = resultSet.getInt("fk_game");	    
	   
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    rating.setGame(g.find(gameId));

	    //recherche du user (id puis objet) 
	    int userId=resultSet.getInt("fk_user");
	    UserDaoImpl u = new UserDaoImpl(daoFactory);
	    rating.setUser(u.find(userId));
	    
	   	 
	    return rating;
	}
	
}
