package bean;

import java.io.Serializable;
import javax.persistence.*;


public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;


	private int idRating;
	private int fkGameRating;
	private int fkUserRating;
	private int ratingRating;
	private Game game;
	private User user;

	public Rating() {
	}

	public int getIdRating() {
		return this.idRating;
	}

	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}

	public int getFkGameRating() {
		return this.fkGameRating;
	}

	public void setFkGameRating(int fkGameRating) {
		this.fkGameRating = fkGameRating;
	}

	public int getFkUserRating() {
		return this.fkUserRating;
	}

	public void setFkUserRating(int fkUserRating) {
		this.fkUserRating = fkUserRating;
	}

	public int getRatingRating() {
		return this.ratingRating;
	}

	public void setRatingRating(int ratingRating) {
		this.ratingRating = ratingRating;
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