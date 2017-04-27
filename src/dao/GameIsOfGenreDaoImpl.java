package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoTools.*;

import bean.GameIsOfGenre;
import bean.Game;
import bean.Genre;

public class GameIsOfGenreDaoImpl implements GameIsOfGenreDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM game_is_of_genre WHERE id_game_is_of_genre = ?";
	private static final String SQL_INSERT = "INSERT INTO game_is_of_genre (fk_game_user_owns_game, fk_genre_user_owns_game) VALUES (?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM game_is_of_genre";
	
	public GameIsOfGenreDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public GameIsOfGenreDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( GameIsOfGenre gameIsOfGenre ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, gameIsOfGenre.getGame(), gameIsOfGenre.getGenre() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de la création de l'association d'un jeu à un genre, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            gameIsOfGenre.setIdGameIsOfGenre((int) valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "Échec de la création de l'association d'un jeu à un genre, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public GameIsOfGenre find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 GameIsOfGenre gameIsOfGenre = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         gameIsOfGenre = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return gameIsOfGenre;
	}
	
	 public List<GameIsOfGenre> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 GameIsOfGenre gameIsOfGenre = null;
		 
		 List<GameIsOfGenre> listOfGameIsOfGenre = new ArrayList<GameIsOfGenre>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         gameIsOfGenre = map( resultSet );
		         listOfGameIsOfGenre.add(gameIsOfGenre);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfGameIsOfGenre;
	 }
	
	private static GameIsOfGenre map( ResultSet resultSet ) throws SQLException {
	    GameIsOfGenre gameIsOfGenre = new GameIsOfGenre();
	    gameIsOfGenre.setIdGameIsOfGenre( resultSet.getInt( "id_gameIsOfGenre" ) );
	   
	    
	    //recherche de l'objet game	 (id puis objet)   
	    int gameId = resultSet.getInt("fk_game_game_is_of_genre");
	    System.out.print("game : "+gameId+"\n");

	    
	    GameDaoImpl ga=new GameDaoImpl(daoFactory);
	    gameIsOfGenre.setGame(ga.find(gameId));
	   
	   
	    //reccherche de l'objet user (id puis objet)  

	    int genreId = resultSet.getInt("fk_genre_game_is_of_genre");
	    System.out.print("user : "+genreId+"\n");	   

	    //GenreDaoImpl ge = new GenreDaoImpl(daoFactory);
	    //gameIsOfGenre.setGenre(ge.find(genreId));
	    
	 
	    return gameIsOfGenre;
	}
	
}
