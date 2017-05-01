package bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;



import com.fasterxml.jackson.annotation.JsonIgnore;

import dao.DaoException;
import dao.DaoFactory;
import dao.GameDao;
import dao.UserDao;

import java.util.List;

@ManagedBean(name = "Game")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private int idGame;	
	private String pictureUrlGame;	
	private float priceGame;	
	private String titleGame;
	
	//@JsonIgnore
	private Rating rating;
	
	private List<Comment> comments;
	private List<GameIsOfGenre> gameIsOfGenres;
	private List<Console> consoles;
	private List<Historic> historics;	
	private List<UserOwnsGame> userOwnsGames;

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

	public void submit() {
         
		System.out.println("Submitted titleGame : "+ titleGame +"\n");
        System.out.println("Submitted priceGame : "+ priceGame +"\n");   
        System.out.println("Submitted pictureUrlGame : "+ pictureUrlGame +"\n");   
        
                    
        DaoFactory fact = DaoFactory.getInstance();
        GameDao gameDao = fact.getGameDao();        
       
    	
    	try {          
            gameDao.create( this );            
        
	    } catch ( DaoException e ) {	        
	        e.printStackTrace();
	    }
    	
    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("sucess.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /* Traitement de la requête et récupération du bean en résultant */
        
    	//form.addComment(idGame, idUser, messageComment );
    }
}