package dao;

import static dao.DaoTools.fermeturesSilencieuses;
import static dao.DaoTools.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import bean.Game;
import bean.User;

@ManagedBean(name = "User")
public class UserDaoImpl implements UserDao{

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id_user = ?";
	private DaoFactory daoFactory;
	private static final String SQL_INSERT = "INSERT INTO user (pwd_user, username_user) VALUES (?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM user ";
	
	public UserDaoImpl()
	{
		daoFactory = DaoFactory.getInstance();
        
	}
	
	public UserDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	public void create( User user ) throws DaoException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, user.getPwdUser(), user.getUsernameUser() );
		     int statut = preparedStatement.executeUpdate();
		     /* Analyse du statut retourné par la requête d'insertion */
		     if ( statut == 0 ) {
		         throw new DaoException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
		     }
		     /* Récupération de l'id auto-généré par la requête d'insertion */
		     valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		     if ( valeursAutoGenerees.next() ) {
		         /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
		         user.setIdUser((int) valeursAutoGenerees.getLong( 1 ) );
		     } else {
		    	 throw new DaoException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		 }
	}
	public User find( int id ) throws DaoException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;

		try {
		    /* Récupération d'une connexion depuis la Factory */
		    connexion = daoFactory.getConnection();
		    preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false,id );
		    resultSet = preparedStatement.executeQuery();
		    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		    System.out.print("passe user\n");
		    if ( resultSet.next() ) {
		        user = map( resultSet );
		    }
		} catch ( SQLException e ) {
		    throw new DaoException( e );
		} finally {
		    fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}

		 return user;
	}
	
	private static User map( ResultSet resultSet ) throws SQLException {
	    User user = new User();
	    System.out.print("passe map\n");
	    user.setIdUser( resultSet.getInt( "id_user" ) );
	    user.setPwdUser( resultSet.getString("pwd_user") );
	    user.setUsernameUser(resultSet.getString("username_user"));//creer object game
	   	    	 
	    return user;
	}
	
	public List<User> findAll () throws DaoException
	 {
		 System.out.print("findAll user\n");
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 User user = null;
		 
		 List<User> listOfUser = new ArrayList<User>();

		 try {
		     /* Récupération d'une connexion depuis la Factory */
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		     resultSet = preparedStatement.executeQuery();
		     /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
		     while ( resultSet.next() ) {
		         user = map( resultSet );
		         listOfUser.add(user);
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		 }

		 return listOfUser;
	 }
}
