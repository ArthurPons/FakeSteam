package bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "UserOwnsGame")
public class UserOwnsGame implements Serializable {
	private static final long serialVersionUID = 1L;

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	private int idUserOwnsGame;
	private int idGame;
	private int idUser;

	private Game game;
	private User user;

	public UserOwnsGame() {
	}

	public int getIdUserOwnsGame() {
		return this.idUserOwnsGame;
	}

	public void setIdUserOwnsGame(int idUserOwnsGame) {
		this.idUserOwnsGame = idUserOwnsGame;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void submit() {
	 	
        System.out.println("Submitted idGame : "+ idGame +"\n");
        System.out.println("Submitted idUser : "+ idUser +"\n");
                        
        /*
        DaoFactory fact = DaoFactory.getInstance();
        UserOwnsGameDao userOwnsGameDao = fact.getUserOwnsGameDao();
        
      
    	
    	try {          
    		userOwnsGameDao.create( this );            
        
	    } catch ( DaoException e ) {	        
	        e.printStackTrace();
	    }

    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("sucess.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
       
    }

}