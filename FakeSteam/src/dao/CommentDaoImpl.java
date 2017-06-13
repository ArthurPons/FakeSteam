package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoTools.*;

import bean.Comment;
import bean.Game;
import bean.User;

public class CommentDaoImpl implements CommentDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM comment WHERE id_comment = ?";
	private static final String SQL_INSERT = "INSERT INTO comment (message_comment, fk_game_comment, fk_user_comment) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM comment";
	private static final String SQL_SELECT_BY_GAME_ID = "SELECT * FROM comment WHERE fk_game_comment = ?";
	
	public CommentDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public CommentDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	public List<Comment> findGameComments(int gameId) throws DaoException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<Comment> listOfComments= new ArrayList<Comment>();
		Comment comment = null;
		
		 try {
		      
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_GAME_ID, false, gameId );
		     resultSet = preparedStatement.executeQuery();
		     
		     
		     while ( resultSet.next() ) {
		         comment = map( resultSet );
		         listOfComments.add(comment);
		         System.out.print("commentaire trouve : "+comment.getMessageComment()+"\n");
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }
		 
		 return listOfComments;		
		
	}
	
	
	
	@Override
	public void create( User user ) throws DaoException
	{
	
		
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	    	System.out.print("ajout d'un nouveau commentaire "+user.getTempComment()+" , "+user.getTempGame()+" , "+user.getIdUser()+"\n");
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, user.getTempComment(), user.getTempGame(), user.getIdUser() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        
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
	
	 public List<Comment> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Comment comment = null;
		 
		 List<Comment> listOfComment = new ArrayList<Comment>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         comment = map( resultSet );
		         listOfComment.add(comment);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfComment;
	 }
	
	 private Comment map (ResultSet resultSet ) throws SQLException {
		 Comment c = new Comment();
		 c.setMessageComment( resultSet.getString("message_comment") );
		 c.setIdGame(resultSet.getInt("fk_game_comment") );
		 c.setIdUser(resultSet.getInt("fk_user_comment") );
		 return c;
	 }
	 /*
	private static Comment map( ResultSet resultSet ) throws SQLException {
	    Comment comment = new Comment();
	    comment.setIdComment( resultSet.getInt( "id_comment" ) );

	    comment.setMessageComment( resultSet.getString("message_comment") );
	   
	    
	    //recherche de l'objet game	 (id puis objet)   
	    int gameId = resultSet.getInt("fk_game_comment");
	    System.out.print("game : "+gameId+"\n");

	    
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    comment.setGame(g.find(gameId));
	   
	   
	    //reccherche de l'objet user (id puis objet)  

	    int userId = resultSet.getInt("fk_user_comment");
	    System.out.print("user : "+userId+"\n");	   

	    UserDaoImpl u = new UserDaoImpl(daoFactory);
	    comment.setUser(u.find(userId));
	    
	 
	    return comment;
	}
	*/
	
}
