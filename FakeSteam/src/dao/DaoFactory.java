package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {

    private static final String FICHIER_PROPERTIES       = "dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";

    private String              url;
    private String              username;
    private String              password;

    DaoFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * M�thode charg�e de r�cup�rer les informations de connexion � la base de
     * donn�es, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DaoFactory getInstance() throws DaoConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new DaoConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
        } catch ( IOException e ) {
            throw new DaoConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DaoConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        DaoFactory instance = new DaoFactory( url, nomUtilisateur, motDePasse );
        return instance;
    }

    /* M�thode charg�e de fournir une connexion � la base de donn�es */
     public Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );
    }

    /*
     * M�thodes de r�cup�ration de l'impl�mentation des diff�rents DAO 
     */
    public GameDao getGameDao() {
    	System.out.print("passe dao\n");
        return  new GameDaoImpl( this );
    }
    
    public UserDao getUserDao()
    {
    	return  new UserDaoImpl(this);
    }
    
    public CommentDao getCommentDao()
    {
    	return  new CommentDaoImpl(this);
    }
    
    public RatingDao getRatingDao()
    {
    	return  new RatingDaoImpl(this);
    }
    
    public HistoricDao getHistoricDao()
    {
    	return new HistoricDaoImpl(this);
    }
    
    public UserOwnsGameDao getUserOwnsGameDao()
    {
    	return new UserOwnsGameDaoImpl(this);
    }
    
    public GenreDao getGenreDao()
    {
    	return new GenreDaoImpl(this);
    }
    
 
    
}