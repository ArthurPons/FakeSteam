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
	private static final String SQL_SELECT_BY_GAMEID = "SELECT name_genre FROM game_is_of_genre, genre WHERE fk_game_game_is_of_genre = ? AND fk_genre_game_is_of_genre=id_genre ";
	private static final String SQL_INSERT = "INSERT INTO game_is_of_genre (fk_game_game_is_of_genre, fk_genre_game_is_of_genre) VALUES (?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM game_is_of_genre";
	private static final String SQL_SELECT_BY_GENREID = "SELECT id_game,title_game,price_game,picture_url_game FROM game_is_of_genre, game WHERE fk_genre_game_is_of_genre = ? AND fk_game_game_is_of_genre=id_game";
	
	
	
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
	
	public List<Game> findGamesByGenre (int genreId) throws DaoException
	{
		Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Game game = null;
		 
		 List<Game> listOfGame = new ArrayList<Game>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_GENREID, false, genreId);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         game = mapGame( resultSet );
		         listOfGame.add(game);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfGame;
	}
	
	public void createSeveral (int idGame, List<Integer> listGenre) throws DaoException
	{
		//System.out.print("id du jeu pour associe un genre a celui ci : "+idGame+"\n");
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        
	        for (Integer g:listGenre)
	        {
	        	//System.out.print("id du genre :"+g+"\n");
	        	preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, idGame, g );
	        	
	 	        int statut = preparedStatement.executeUpdate();
	 	        /* Analyse du statut retourné par la requête d'insertion */
	 	        if ( statut == 0 ) {
	 	            throw new DaoException( "Échec de la création de l'association d'un jeu à un genre, aucune ligne ajoutée dans la table." );
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
	public List<String> find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 GameIsOfGenre gameIsOfGenre = null;
		 String genreName=null;
		 List<String> listOfGenreName = new ArrayList<String>();
		 
		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_GAMEID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         //gameIsOfGenre = map( resultSet );		    	 
		    	 genreName = resultSet.getString("name_genre");
		    	 //System.out.print("le jeu est de type "+genreName+"\n");
		    	 listOfGenreName.add(genreName);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfGenreName;
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
	
	 private static Game mapGame( ResultSet resultSet ) throws SQLException {
		    Game game = new Game();
		    game.setIdGame( resultSet.getInt( "id_game" ) );
		    game.setPictureUrlGame( resultSet.getString("picture_url_game") );
		    game.setPriceGame(resultSet.getFloat("price_game"));//creer object game
		    game.setTitleGame(resultSet.getString("title_game"));
		    	 
		    return game;
		}
	 
	private static GameIsOfGenre map( ResultSet resultSet ) throws SQLException {
	    GameIsOfGenre gameIsOfGenre = new GameIsOfGenre();
	    gameIsOfGenre.setIdGameIsOfGenre( resultSet.getInt( "id_gameIsOfGenre" ) );
	   
	    
	    //recherche de l'objet game	 (id puis objet)   
	    int gameId = resultSet.getInt("fk_game_game_is_of_genre");
	    //System.out.print("game : "+gameId+"\n");

	    
	    GameDaoImpl ga=new GameDaoImpl(daoFactory);
	    gameIsOfGenre.setGame(ga.find(gameId));
	   
	   
	    //reccherche de l'objet user (id puis objet)  

	    int genreId = resultSet.getInt("fk_genre_game_is_of_genre");
	    //System.out.print("user : "+genreId+"\n");	   

	    //GenreDaoImpl ge = new GenreDaoImpl(daoFactory);
	    //gameIsOfGenre.setGenre(ge.find(genreId));
	    
	 
	    return gameIsOfGenre;
	}
	
}
