package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoTools.*;

import bean.GameIsOnConsole;
import bean.Game;
import bean.User;

public class GameIsOnConsoleDaoImpl implements GameIsOnConsoleDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_GAMEID = "select name_console from console, game_is_on_console where game_is_on_console.fk_game=? AND game_is_on_console.fk_console=console.id_console";
	private static final String SQL_INSERT = "INSERT INTO game_is_on_console (fk_game, fk_console) VALUES (?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM game_is_on_console";
	private static final String SQL_SELECT_BY_CONSOLEID = "SELECT id_game,title_game,price_game,picture_url_game FROM game_is_on_console, game WHERE fk_console = ? AND fk_game=id_game";
	
	
	public GameIsOnConsoleDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public GameIsOnConsoleDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( GameIsOnConsole game_is_on_console ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, game_is_on_console.getGame(), game_is_on_console.getConsole() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "�chec de la cr�ation de l'utilisateur, aucune ligne ajout�e dans la table." );
	        }
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri�t� id du bean Utilisateur avec sa valeur */
	            game_is_on_console.setIdGameIsOnConsole((int) valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "�chec de la cr�ation de l'utilisateur en base, aucun ID auto-g�n�r� retourn�." );
	        }
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	 public void createSeveral(int idGame, List<Integer> listConsoles) throws DaoException
	 {
		 //System.out.print("id du jeu pour associe un genre a celui ci : "+idGame+"\n");
			Connection connexion = null;
		    PreparedStatement preparedStatement = null;
		    ResultSet valeursAutoGenerees = null;

		    try {
		        /* R�cup�ration d'une connexion depuis la Factory */
		        connexion = daoFactory.getConnection();
		        
		        for (Integer c:listConsoles)
		        {
		        	//System.out.print("id du genre :"+c+"\n");
		        	preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, idGame, c );
		        	
		 	        int statut = preparedStatement.executeUpdate();
		 	        /* Analyse du statut retourn� par la requ�te d'insertion */
		 	        if ( statut == 0 ) {
		 	            throw new DaoException( "�chec de la cr�ation de l'association d'un jeu � un genre, aucune ligne ajout�e dans la table." );
		 	        }
		 	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
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
		
		 String consoleName = null;
		 List<String> listConsoleName = new ArrayList<String>();

		 try {
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_GAMEID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
		    while ( resultSet.next() ) {
		    	
		    	 consoleName = resultSet.getString("name_console");
		    	 //System.out.print("le jeu est present sur "+consoleName+"\n");
		    	 listConsoleName.add(consoleName);
		    	 //System.out.print("ajout dans la liste de  "+consoleName+"\n");
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listConsoleName;
	}
	
	public List<Game> findGamesByConsole(int consoleId) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		
		 Game g;
		 List<Game> listGame = new ArrayList<Game>();

		 try {
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_CONSOLEID, false, consoleId );
		     resultSet = preparedStatement.executeQuery();
		     
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
		    while ( resultSet.next() ) {
		    	
		    	 g= mapGame(resultSet);
		    	 System.out.print("jeu trouve (console) :"+g.getTitleGame()+"\n");
		    	 listGame.add(g);
		    	 
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listGame;
	}
	
	 private static Game mapGame( ResultSet resultSet ) throws SQLException {
		    Game game = new Game();
		    game.setIdGame( resultSet.getInt( "id_game" ) );
		    game.setPictureUrlGame( resultSet.getString("picture_url_game") );
		    game.setPriceGame(resultSet.getFloat("price_game"));//creer object game
		    game.setTitleGame(resultSet.getString("title_game"));
		    	 
		    return game;
		}
	 
	 
	 public List<GameIsOnConsole> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 GameIsOnConsole game_is_on_console = null;
		 
		 List<GameIsOnConsole> listOfGameIsOnConsole = new ArrayList<GameIsOnConsole>();

		 try {
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
		     while ( resultSet.next() ) {
		         game_is_on_console = map( resultSet );
		         listOfGameIsOnConsole.add(game_is_on_console);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfGameIsOnConsole;
	 }
	
	private static GameIsOnConsole map( ResultSet resultSet ) throws SQLException {
	    GameIsOnConsole game_is_on_console = new GameIsOnConsole();
	    game_is_on_console.setIdGameIsOnConsole( resultSet.getInt( "id_game_is_on_console" ) );  
	    
	    //recherche de l'objet game	 (id puis objet)   
	    int gameId = resultSet.getInt("fk_game_game_is_on_console");
	    //System.out.print("game : "+gameId+"\n");

	    
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    game_is_on_console.setGame(g.find(gameId));
	   
	   
	    //recherche de l'objet console (id puis objet)  
	    int consoleId = resultSet.getInt("fk_console_game_is_on_console");
	    //System.out.print("console : "+consoleId+"\n");	   

	    ConsoleDaoImpl u = new ConsoleDaoImpl(daoFactory);
	    game_is_on_console.setConsole(u.find(consoleId));
	    
	 
	    return game_is_on_console;
	}
	
}
