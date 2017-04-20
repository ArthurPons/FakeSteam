package servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import bean.Game;
import bean.User;
import dao.DaoException;
import dao.GameDao;
import dao.UserDao;

public final class DbFormUser {
    private static final String CHAMP_USERNAME = "username";
    private static final String CHAMP_PWD = "pwd";
    
   
    private String resultat;
    private Map<String, String> erreurs          = new HashMap<String, String>();
    private UserDao userDao;

    public DbFormUser( UserDao userDao ) {
        this.userDao = userDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public User addUser( HttpServletRequest request ) {
        String username = getValeurChamp( request, CHAMP_USERNAME );
        String pwd = getValeurChamp( request, CHAMP_PWD );
        
        
        System.out.print("username :"+username+"\n");
        System.out.print("pwd"+pwd+"\n");
        User user = new User();
        user.setUsernameUser(username);
        user.setPwdUser(pwd);
           
        
        try {          
                userDao.create( user );
                resultat = "Succès de l'inscription.";
            
        } catch ( DaoException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return user;
    }



    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}