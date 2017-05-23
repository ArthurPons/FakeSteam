package bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Comment")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idComment;
	private String messageComment;
	private Game game;
	private User user;
	
	private int idGame;
	private int idUser;

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

	public Comment() {
	}

	public int getIdComment() {
		return this.idComment;
	}

	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}

	public String getMessageComment() {
		return this.messageComment;
	}

	public void setMessageComment(String messageComment) {
		this.messageComment = messageComment;
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
	
	public int getGameId()
	{
		return game.getIdGame();
	}

	public int getUserId()
	{
		return user.getIdUser();
	}
	
	public void submit() {
		 	
        System.out.println("Submitted idGame : "+ idGame +"\n");
        System.out.println("Submitted idUser : "+ idUser +"\n");
        System.out.println("Submitted comment : "+ messageComment +"\n");   
          
        /*
        DaoFactory fact = DaoFactory.getInstance();
        CommentDao commentDao = fact.getCommentDao();
       
    	try {          
            commentDao.create( this );            
        
	    } catch ( DaoException e ) {	        
	        e.printStackTrace();
	    }

    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("sucess.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
    	//form.addComment(idGame, idUser, messageComment );
    	 */
    	 
    }
}