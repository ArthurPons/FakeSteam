package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoTools.*;

import bean.UserOwnsGame;
import bean.Game;
import bean.User;

public class UserOwnsGameDaoImpl implements UserOwnsGameDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user_owns_game WHERE id_user_owns_game = ?";
	private static final String SQL_INSERT = "INSERT INTO user_owns_game (fk_game_own, fk_user_own) VALUES (?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM user_owns_game";
	
	public UserOwnsGameDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public UserOwnsGameDaoImpl(DaoFactory dao)
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
	        for(Game g : user.getPanier())
	        {
	        	preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, g.getIdGame(), user.getIdUser() );
	        	System.out.print("ajout du jeu"+g.getTitleGame()+" dans les jeux appartenant a "+user.getUsernameUser()+"\n");
		        int statut = preparedStatement.executeUpdate();
		        /* Analyse du statut retourné par la requête d'insertion */
		        if ( statut == 0 ) {
		            throw new DaoException( "Échec de la création de la possession d'un jeu par un utilisateur, aucune ligne ajoutée dans la table." );
		        }
		        /* Récupération de l'id auto-généré par la requête d'insertion */
		        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		       
	        }
	        
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public UserOwnsGame find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 UserOwnsGame userOwnsGame = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         userOwnsGame = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return userOwnsGame;
	}
	
	 public List<UserOwnsGame> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 UserOwnsGame userOwnsGame = null;
		 
		 List<UserOwnsGame> listOfUserOwnsGame = new ArrayList<UserOwnsGame>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         userOwnsGame = map( resultSet );
		         listOfUserOwnsGame.add(userOwnsGame);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfUserOwnsGame;
	 }
	
	private static UserOwnsGame map( ResultSet resultSet ) throws SQLException {
	    UserOwnsGame userOwnsGame = new UserOwnsGame();
	    userOwnsGame.setIdUserOwnsGame( resultSet.getInt( "id_userOwnsGame" ) );
	   
	    
	    //recherche de l'objet game	 (id puis objet)   
	    int gameId = resultSet.getInt("fk_game_own");
	    System.out.print("game : "+gameId+"\n");

	    
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    userOwnsGame.setGame(g.find(gameId));
	   
	   
	    //recherche de l'objet user (id puis objet)  

	    int userId = resultSet.getInt("fk_user_own");
	    System.out.print("user : "+userId+"\n");	   

	    UserDaoImpl u = new UserDaoImpl(daoFactory);
	    userOwnsGame.setUser(u.find(userId));
	    
	 
	    return userOwnsGame;
	}
	
}
