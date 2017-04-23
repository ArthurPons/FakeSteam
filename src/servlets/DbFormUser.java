package servlets;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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


    public void addUser( HttpServletRequest request ) throws NoSuchAlgorithmException {
        String username = getValeurChamp( request, CHAMP_USERNAME );
        String pwd = getValeurChamp( request, CHAMP_PWD );
        
        //On hash le mdp
        byte[] salt = getSalt();
        String securePassword = get_SHA_256_SecurePassword(pwd, salt);
        
        User user = new User();
        user.setUsernameUser(username);
        user.setPwdUser(securePassword);
        user.setSaltUser(salt);
        
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
    
    /*
     * Méthode utilitaire qui retourne le hash d'un mdp
     */
    private static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
    {
    	String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
    /*
     * Méthode utilitaire pour ajouter du sel à un mdp 
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}