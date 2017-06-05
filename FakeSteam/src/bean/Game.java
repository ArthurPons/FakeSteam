package bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.primefaces.model.UploadedFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dao.DaoException;
import dao.DaoFactory;
import dao.GameDao;
import dao.UserDao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown=true)

public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private int idGame;	
	private String pictureUrlGame;	
	private float priceGame;	
	private String titleGame;
		
	private UploadedFile uploadedFile;	
	private int consoleId;
	private List<Genre> listOfGenre;
	private List<Integer> listOfGenreId;
	private List<Integer> listOfConsoleId;

	private List<String> listOfGenreName;
	private List<String> listOfConsoleName;
	
	
	//@JsonIgnore
	private Rating rating;
	
	private List<Comment> comments;
	private List<GameIsOfGenre> gameIsOfGenres;
	private List<Console> consoles;
	private List<Historic> historics;	
	private List<UserOwnsGame> userOwnsGames;

	
	
	public List<String> getListOfGenreName() {
		return listOfGenreName;
	}

	public void setListOfGenreName(List<String> listOfGenreName) {
		this.listOfGenreName = listOfGenreName;
	}

	public List<String> getListOfConsoleName() {
		return listOfConsoleName;
	}

	public void setListOfConsoleName(List<String> listOfConsoleName) {
		this.listOfConsoleName = listOfConsoleName;
	}

	public List<Integer> getListOfConsoleId() {
		return listOfConsoleId;
	}

	public void setListOfConsoleId(List<Integer> listOfConsoleId) {
		this.listOfConsoleId = listOfConsoleId;
	}

	public int getConsoleId() {
		return consoleId;
	}

	public void setConsoleId(int consoleId) {
		this.consoleId = consoleId;
	}

	public List<Integer> getListOfGenreId() {
		return listOfGenreId;
	}

	public void setListOfGenreId(List<Integer> listOfGenreId) {
		this.listOfGenreId = listOfGenreId;
	}

	public List<Genre> getListOfGenre() {
		return listOfGenre;
	}

	public void setListOfGenre(List<Genre> listOfGenre) {
		this.listOfGenre = listOfGenre;
	}

	@JsonIgnore
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public Game() {
	}

	public int getIdGame() {
		return this.idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getPictureUrlGame() {
		return this.pictureUrlGame;
	}

	public void setPictureUrlGame(String pictureUrlGame) {
		this.pictureUrlGame = pictureUrlGame;
	}

	public float getPriceGame() {
		return this.priceGame;
	}

	public void setPriceGame(float priceGame) {
		this.priceGame = priceGame;
	}

	public String getTitleGame() {
		return this.titleGame;
	}

	public void setTitleGame(String titleGame) {
		this.titleGame = titleGame;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setGame(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setGame(null);

		return comment;
	}

	public List<GameIsOfGenre> getGameIsOfGenres() {
		return this.gameIsOfGenres;
	}

	public void setGameIsOfGenres(List<GameIsOfGenre> gameIsOfGenres) {
		this.gameIsOfGenres = gameIsOfGenres;
	}

	public GameIsOfGenre addGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().add(gameIsOfGenre);
		gameIsOfGenre.setGame(this);

		return gameIsOfGenre;
	}

	public GameIsOfGenre removeGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().remove(gameIsOfGenre);
		gameIsOfGenre.setGame(null);

		return gameIsOfGenre;
	}

	public List<Console> getConsoles() {
		return this.consoles;
	}

	public void setConsoles(List<Console> consoles) {
		this.consoles = consoles;
	}

	public List<Historic> getHistorics() {
		return this.historics;
	}

	public void setHistorics(List<Historic> historics) {
		this.historics = historics;
	}

	public Historic addHistoric(Historic historic) {
		getHistorics().add(historic);
		historic.setGame(this);

		return historic;
	}

	public Historic removeHistoric(Historic historic) {
		getHistorics().remove(historic);
		historic.setGame(null);

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
		userOwnsGame.setGame(this);

		return userOwnsGame;
	}

	public UserOwnsGame removeUserOwnsGame(UserOwnsGame userOwnsGame) {
		getUserOwnsGames().remove(userOwnsGame);
		userOwnsGame.setGame(null);

		return userOwnsGame;
	}
	
	

}