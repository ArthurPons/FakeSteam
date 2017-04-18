package bean;

import java.io.Serializable;
import javax.persistence.*;



public class UserOwnsGame implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idUserOwnsGame;

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

}