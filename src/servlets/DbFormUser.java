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
   
    private UserDao userDao;

    public DbFormUser( UserDao userDao ) {
        this.userDao = userDao;
    }


    public void addUser( HttpServletRequest request ) {
        String username = getValeurChamp( request, CHAMP_USERNAME );
        String pwd = getValeurChamp( request, CHAMP_PWD );
        
      
        User user = new User();
        user.setUsernameUser(username);
        user.setPwdUser(pwd);
           
        
        try {          
                userDao.create( user );
               
            
        } catch ( DaoException e ) {            
            e.printStackTrace();
        }
       
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