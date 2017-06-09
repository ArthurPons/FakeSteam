package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoTools.*;

import bean.Game;
import bean.Rating;
import bean.User;

public class RatingDaoImpl implements RatingDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM rating WHERE id_rating = ?";
	private static final String SQL_INSERT = "INSERT INTO rating (fk_game_rating, fk_user_rating, rating_rating) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_BY_USER_GAME = "SELECT * FROM rating WHERE  fk_user_rating = ? AND fk_game_rating = ? ";
	private static final String SQL_SELECT_BY_GAME = "SELECT * FROM rating WHERE  fk_game_rating = ? ";
	
	
	public RatingDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public RatingDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( User user ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, user.getTempGame() , user.getIdUser(), user.getTempRate());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de l'ajout d'une ligne dans la table historic, aucune ligne ajoutée." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	public List<Integer> findGameRating (int gameId) throws DaoException
	{
		Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 int rating = 0;

		 List<Integer> listOfInteger = new ArrayList<Integer>();
		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_GAME, false, gameId );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		    	 //resultSet.getInt("fk_game");
		         rating = resultSet.getInt("rating_rating");
		         listOfInteger.add(rating);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }
		 
		 return listOfInteger;
	}
	
	public int findUserRating (int userId, int gameId) throws DaoException
	{
		Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 int rating = -1;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_USER_GAME, false, userId, gameId );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		    	 //resultSet.getInt("fk_game");
		         rating = resultSet.getInt("rating_rating");
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }
		 
		 return rating;
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
		/*
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
	    */
		return null;
	}
	
}
