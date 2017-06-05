package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import static dao.DaoTools.*;
import bean.Comment;
import bean.Game;

@ManagedBean(name = "GameDao")
public class GameDaoImpl implements GameDao{

	private DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM game WHERE id_game = ?";
	private static final String SQL_INSERT = "INSERT INTO game (picture_url_game, price_game, title_game) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM game";
	private static final String SQL_SELECT_ALL_USER_GAMES = "SELECT  g.id_game, g.title_game, g.price_game, g.picture_url_game FROM game AS g, user_owns_game as u WHERE g.id_game=u.fk_game_own and u.fk_user_own=?";
	private static final String SQL_SELECT_BY_NAME ="SELECT * FROM game WHERE title_game = ?";
	
	public GameDaoImpl()
	{
		daoFactory = DaoFactory.getInstance();
        
	}
	
	public GameDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	 public int create( Game game ) throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet valeursAutoGenerees = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, game.getPictureUrlGame(), game.getPriceGame(), game.getTitleGame() );
		     int statut = preparedStatement.executeUpdate();
		     /* Analyse du statut retourné par la requête d'insertion */
		     if ( statut == 0 ) {
		         throw new DaoException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
		     }
		     /* Récupération de l'id auto-généré par la requête d'insertion */
		     valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		     if ( valeursAutoGenerees.next() ) {
		         /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
		    	 
		         game.setIdGame((int) valeursAutoGenerees.getLong( 1 ) );
		     } else {
		    	 throw new DaoException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		 }
		 return game.getIdGame();
	 }
	 
	public Game findByName (String gameName) throws DaoException
	{
		Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Game game = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_NAME, true, gameName);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         game = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return game;
	}
	
	@Override
	 public Game find( int id ) throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Game game = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         game = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return game;
	 }
	 
	 private static Game map( ResultSet resultSet ) throws SQLException {
		    Game game = new Game();
		    game.setIdGame( resultSet.getInt( "id_game" ) );
		    game.setPictureUrlGame( resultSet.getString("picture_url_game") );
		    game.setPriceGame(resultSet.getFloat("price_game"));//creer object game
		    game.setTitleGame(resultSet.getString("title_game"));
		    	 
		    return game;
		}
	 
	 
	 public List<Game> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Game game = null;
		 
		 List<Game> listOfGame = new ArrayList<Game>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         game = map( resultSet );
		         listOfGame.add(game);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfGame;
	 }
	 
	 
	 public List<Game> findAllUserGame(int idUser) throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Game game = null;
		 
		 List<Game> listOfGame = new ArrayList<Game>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL_USER_GAMES, true,idUser);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         game = map( resultSet );
		         listOfGame.add(game);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfGame;
	 }
	 
	 
}
