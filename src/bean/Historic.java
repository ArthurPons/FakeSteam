package bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;



public class Historic implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idHistoric;

	private Date dateHistoric;

	private Game game;

	private User user;

	public Historic() {
	}

	public int getIdHistoric() {
		return this.idHistoric;
	}

	public void setIdHistoric(int idHistoric) {
		this.idHistoric = idHistoric;
	}

	public Date getDateHistoric() {
		return this.dateHistoric;
	}

	public void setDateHistoric(Date dateHistoric) {
		this.dateHistoric = dateHistoric;
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