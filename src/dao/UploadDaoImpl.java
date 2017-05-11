package dao;

import static dao.DaoTools.fermeturesSilencieuses;
import static dao.DaoTools.initialisationRequetePreparee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Game;
import bean.Upload;

public class UploadDaoImpl implements UploadDao{

	private DaoFactory daoFactory;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM image WHERE id_image = ?";
	private static final String SQL_INSERT = "INSERT INTO image (image_image, name_image, length_image) VALUES (?, ?, ?)";
	//private static final String SQL_SELECT_ALL = "SELECT * FROM game";
	
	public UploadDaoImpl()
	{
		daoFactory = DaoFactory.getInstance();
        
	}
	
	public UploadDaoImpl(DaoFactory dao)
	{
		daoFactory=dao;
	}
	
	@Override
	 public void create( Upload upload ) throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet valeursAutoGenerees = null;

		 
		 
		 try {
			 
			 File imgfile = new File(upload.getUploadedFile().getName());
			 System.out.println("Name: " + imgfile.getName());
			 FileInputStream fin = null;
			try {
				fin = new FileInputStream(imgfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		     
		     connexion = daoFactory.getConnection();
		     preparedStatement = connexion.prepareStatement(SQL_INSERT);
		     preparedStatement.setBinaryStream(1,fin,(int)imgfile.length());
		     preparedStatement.setString(2, imgfile.getName());
		     preparedStatement.setLong(3, upload.getUploadedFile().getSize());
		    
		     
		    
		     int statut = preparedStatement.executeUpdate();
		     /* Analyse du statut retourné par la requête d'insertion */
		     if ( statut == 0 ) {
		         throw new DaoException( "Échec de l'insertion de l'image" );
		     }
		     /* Récupération de l'id auto-généré par la requête d'insertion */
		     valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		     if ( valeursAutoGenerees.next() ) {
		         /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
		    	 
		         //upload.setId_image((int) valeursAutoGenerees.getLong( 1 ) );
		     } else {
		    	 throw new DaoException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
		     }
		 } catch ( SQLException e ) {
		     throw new DaoException( e );
		 } finally {
		     fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		 }
	 }
	 
	/*
	@Override
	 public Upload find( int id ) throws DaoException
	 {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Game game = null;

		 try {
		   
		     connexion = daoFactory.getConnection();
		     preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_BY_ID, false, id);
		     resultSet = preparedStatement.executeQuery();
		    
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
	 */
	 
	 private static Game map( ResultSet resultSet ) throws SQLException {
		    Game game = new Game();
		    game.setIdGame( resultSet.getInt( "id_game" ) );
		    game.setPictureUrlGame( resultSet.getString("picture_url_game") );
		    game.setPriceGame(resultSet.getFloat("price_game"));//creer object game
		    game.setTitleGame(resultSet.getString("title_game"));
		    	 
		    return game;
		}
}
