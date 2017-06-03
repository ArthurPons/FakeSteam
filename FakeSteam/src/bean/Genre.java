package bean;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idGenre;


	private String nameGenre;

	private List<GameIsOfGenre> gameIsOfGenres;

	public Genre() {
	}

	public int getIdGenre() {
		return this.idGenre;
	}

	public void setIdGenre(int idGenre) {
		this.idGenre = idGenre;
	}

	public String getNameGenre() {
		return this.nameGenre;
	}

	public void setNameGenre(String nameGenre) {
		this.nameGenre = nameGenre;
	}

	public List<GameIsOfGenre> getGameIsOfGenres() {
		return this.gameIsOfGenres;
	}

	public void setGameIsOfGenres(List<GameIsOfGenre> gameIsOfGenres) {
		this.gameIsOfGenres = gameIsOfGenres;
	}

	public GameIsOfGenre addGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().add(gameIsOfGenre);
		gameIsOfGenre.setGenre(this);

		return gameIsOfGenre;
	}

	public GameIsOfGenre removeGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().remove(gameIsOfGenre);
		gameIsOfGenre.setGenre(null);

		return gameIsOfGenre;
	}

}