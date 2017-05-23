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
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM game_is_on_console WHERE id_game_is_on_console = ?";
	private static final String SQL_INSERT = "INSERT INTO game_is_on_console (fk_game_game_is_on_console, fk_console_game_is_on_console) VALUES (?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM game_is_on_console";
	
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
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, game_is_on_console.getGame(), game_is_on_console.getConsole() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            game_is_on_console.setIdGameIsOnConsole((int) valeursAutoGenerees.getLong( 1 ) );
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
	public GameIsOnConsole find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 GameIsOnConsole game_is_on_console = null;

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     if ( resultSet.next() ) {
		         game_is_on_console = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return game_is_on_console;
	}
	
	 public List<GameIsOnConsole> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 GameIsOnConsole game_is_on_console = null;
		 
		 List<GameIsOnConsole> listOfGameIsOnConsole = new ArrayList<GameIsOnConsole>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
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
	    System.out.print("game : "+gameId+"\n");

	    
	    GameDaoImpl g=new GameDaoImpl(daoFactory);
	    game_is_on_console.setGame(g.find(gameId));
	   
	   
	    //recherche de l'objet console (id puis objet)  
	    int consoleId = resultSet.getInt("fk_console_game_is_on_console");
	    System.out.print("console : "+consoleId+"\n");	   

	    ConsoleDaoImpl u = new ConsoleDaoImpl(daoFactory);
	    game_is_on_console.setConsole(u.find(consoleId));
	    
	 
	    return game_is_on_console;
	}
	
}
