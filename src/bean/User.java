package bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;

import dao.DaoException;
import dao.DaoFactory;
import dao.UserDao;


import java.util.List;

@ManagedBean(name = "User")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idUser;
	private String pwdUser;
	private byte[] saltUser;
	private String usernameUser;
	
	private Rating rating;
	
	private List<Comment> comments;
	private List<Historic> historics;
	private List<UserOwnsGame> userOwnsGames;

	public User() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getPwdUser() {
		return this.pwdUser;
	}

	public void setPwdUser(String pwdUser) {
		this.pwdUser = pwdUser;
	}
	
	public byte[] getSaltUser() {
		return this.saltUser;
	}
	
	public void setSaltUser(byte[] salt) {
		this.saltUser = salt;
	}


	public String getUsernameUser() {
		return this.usernameUser;
	}

	public void setUsernameUser(String usernameUser) {
		this.usernameUser = usernameUser;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setUser(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setUser(null);

		return comment;
	}

	public List<Historic> getHistorics() {
		return this.historics;
	}

	public void setHistorics(List<Historic> historics) {
		this.historics = historics;
	}

	public Historic addHistoric(Historic historic) {
		getHistorics().add(historic);
		historic.setUser(this);

		return historic;
	}

	public Historic removeHistoric(Historic historic) {
		getHistorics().remove(historic);
		historic.setUser(null);

		return historic;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public List<UserOwnsGame> getUserOwnsGames() {
		return this.userOwnsGames;
	}

	public void setUserOwnsGames(List<UserOwnsGame> userOwnsGames) {
		this.userOwnsGames = userOwnsGames;
	}

	public UserOwnsGame addUserOwnsGame(UserOwnsGame userOwnsGame) {
		getUserOwnsGames().add(userOwnsGame);
		userOwnsGame.setUser(this);

		return userOwnsGame;
	}

	public UserOwnsGame removeUserOwnsGame(UserOwnsGame userOwnsGame) {
		getUserOwnsGames().remove(userOwnsGame);
		userOwnsGame.setUser(null);

		return userOwnsGame;
	}

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
	
	
	public void submit() {
	 		                
		System.out.println("Submitted idUser : "+ usernameUser +"\n");
        System.out.println("Submitted pwd : "+ pwdUser +"\n");   
        
        try {
			saltUser = getSalt();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String securePassword = get_SHA_256_SecurePassword(pwdUser, saltUser);
        pwdUser = securePassword;
        
        
        DaoFactory fact = DaoFactory.getInstance();
        UserDao userDao = fact.getUserDao();        
       
    	
    	try {          
            userDao.create( this );            
        
	    } catch ( DaoException e ) {	        
	        e.printStackTrace();
	    }
    	
    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("sucess.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /* Traitement de la requête et récupération du bean en résultant */
        
    	//form.addComment(idGame, idUser, messageComment );
    }
}