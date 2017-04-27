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
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, gameIsOfGenre.getGame(), gameIsOfGenre.getGenre() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "�chec de la cr�ation de l'association d'un jeu � un genre, aucune ligne ajout�e dans la table." );
	        }
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri�t� id du bean Utilisateur avec sa valeur */
	            gameIsOfGenre.setIdGameIsOfGenre((int) valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "�chec de la cr�ation de l'association d'un jeu � un genre, aucun ID auto-g�n�r� retourn�." );
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
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
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
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
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
