package dao;

import static dao.DaoTools.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Console;

public class ConsoleDaoImpl implements ConsoleDao{

	private static DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM console WHERE id_console = ?";
	private static final String SQL_INSERT = "INSERT INTO console (name_console,type_console) VALUES (?,?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM console";
	
	public ConsoleDaoImpl()
	{
		daoFactory= DaoFactory.getInstance();
	}
	
	public ConsoleDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	public void create( Console console ) throws DaoException
	{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, console.getNameConsole(), console.getTypeConsole());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "�chec de la cr�ation de la console, aucune ligne ajout�e dans la table." );
	        }
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri�t� id du bean Console avec sa valeur */
	            console.setIdConsole((int) valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "�chec de la cr�ation de la console, aucun ID auto-g�n�r� retourn�." );
	        }
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public Console find( int id ) throws DaoException
	{
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Console console = null;

		 try {
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id );
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
		     if ( resultSet.next() ) {
		         console = map( resultSet );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return console;
	}
	
	 public List<Console> findAll () throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Console console = null;
		 
		 List<Console> listOfConsole = new ArrayList<Console>();

		 try {
		     /* R�cup�ration d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
		     while ( resultSet.next() ) {
		         console = map( resultSet );
		         listOfConsole.add(console);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfConsole;
	 }
	
	private static Console map( ResultSet resultSet ) throws SQLException {
	    Console console = new Console();
	    console.setIdConsole( resultSet.getInt( "id_console" ) );

	    console.setNameConsole( resultSet.getString("name_console") );	
	    
	    console.setTypeConsole(resultSet.getString("type_console"));;
	    
	    //recherche de l'objet game	 (id puis objet)   
	    int consoleId = resultSet.getInt("id_console");
	    System.out.print("console : "+consoleId+"\n");
	    
	    return console;
	}
}
