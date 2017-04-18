package bean;

import java.io.Serializable;
import javax.persistence.*;



public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idComment;
	private String messageComment;
	private Game game;
	private User user;

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
}