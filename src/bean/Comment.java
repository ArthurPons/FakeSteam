package bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;

import dao.CommentDao;
import dao.DaoFactory;
import servlets.DbFormComment;



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
                
        DaoFactory fact = DaoFactory.getInstance();
        CommentDao commentDao = fact.getCommentDao();
        
        DbFormComment form = new DbFormComment( commentDao );

        /* Traitement de la requête et récupération du bean en résultant */
        
    	form.addComment(idGame, idUser, messageComment );
    }
}