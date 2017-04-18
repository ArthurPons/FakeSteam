package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DaoTools.*;

import bean.Comment;
import bean.Game;
import bean.User;

public class CommentDaoImpl implements CommentDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM comment WHERE id_comment = ?";
	private static final String SQL_INSERT = "INSERT INTO comment (message_comment, fk_game_comment, fk_user_comment) VALUES (?, ?, ?)";
	
	public CommentDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public CommentDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( Comment comment ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, comment.getMessageComment(), comment.getGameId(), comment.getUserId() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            comment.setIdComment((int) valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public Comment find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Comment comment = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         comment = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return comment;
	}
	
	
	private static Comment map( ResultSet resultSet ) throws SQLException {
	    Comment comment = new Comment();
	    comment.setIdComment( resultSet.getInt( "id_comment" ) );
	    comment.setMessageComment( resultSet.getString("message_comment") );
	   
	    
	    //recherche de l'objet game
	    int gameId = resultSet.getInt("fk_game_comment");
	    System.out.print("game : "+gameId+"\n");
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    comment.setGame(g.find(gameId));
	    
	    //reccherche de l'objet user
	    int userId = resultSet.getInt("fk_user_comment");
	    System.out.print("user : "+userId+"\n");
	    UserDaoImpl u = new UserDaoImpl(daoFactory);
	    comment.setUser(u.find(userId));
	    
	 
	    return comment;
	}
	
}
