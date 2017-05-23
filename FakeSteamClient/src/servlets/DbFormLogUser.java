package servlets;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import bean.User;

import java.util.List;

public final class DbFormLogUser {
	/*
    private static final String CHAMP_USERNAME = "username";
    private static final String CHAMP_PWD = "pwd";
   
    private UserDao userDao;

    
    public DbFormLogUser( UserDao userDao ) {
        this.userDao = userDao;
    }
    


    public Optional<User> authentificateUser( HttpServletRequest request ) throws NoSuchAlgorithmException {
        String username = getValeurChamp( request, CHAMP_USERNAME );
        String pwd = getValeurChamp( request, CHAMP_PWD );
        System.out.println(username);
        
        List<User> listUsers = this.userDao.findAll();
        System.out.println(listUsers);
        Optional<User> loggedUser = listUsers.stream()
        		 				   .filter(user -> username.equals(user.getUsernameUser()) 
        		 						   		   && DbFormLogUser.get_SHA_256_SecurePassword(pwd, user.getSaltUser()).equals(user.getPwdUser()))
        		 				   .findAny();
        
        return loggedUser;
    }

    
      //Méthode utilitaire qui retourne null si un champ est vide, et son contenu
      sinon.
     
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
    
     //Méthode utilitaire qui retourne le hash d'un mdp
     
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
     
    
     //Méthode utilitaire pour ajouter du sel à un mdp 
     
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    */
}