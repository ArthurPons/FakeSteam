package bean;

import java.io.Serializable;
import javax.persistence.*;


public class GameIsOfGenre implements Serializable {
	private static final long serialVersionUID = 1L;


	private int idGameIsOfGenre;

	private Game game;

	private Genre genre;

	public GameIsOfGenre() {
	}

	public int getIdGameIsOfGenre() {
		return this.idGameIsOfGenre;
	}

	public void setIdGameIsOfGenre(int idGameIsOfGenre) {
		this.idGameIsOfGenre = idGameIsOfGenre;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}