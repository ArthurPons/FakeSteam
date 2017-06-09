package rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.SerializationFeature;


import bean.Game;
import bean.Rating;
import bean.User;

import dao.DaoFactory;
import dao.GameIsOfGenreDao;

@Path("game")
public class GameRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> printGameById(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
		Game g = gameDao.find(i);
		List<Game> lg = new ArrayList<Game>();
		lg.add(g);
		lg.add(new Game());
		return lg;

		
	}
	
	@GET
	@Path("/getRating/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Integer> getRating(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
		dao.RatingDao ratingDao = fact.getRatingDao();
		
		List<Integer> li = ratingDao.findGameRating(i);
		
		return li;

		
	}
	
	@GET
	@Path("/getbyName/{s}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> printGameById(@PathParam("s") String s) {
		
		System.out.print("nom du jeu :"+s+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
		Game g = gameDao.findByName(s);
		System.out.print("id du jeu trouve :"+g.getIdGame()+"\n" );
		List<Game> listGame = new ArrayList<Game>();
		listGame.add(g);
		listGame.add(new Game());
		return listGame;

		
	}
	
	
	@GET
	@Path("/gameByGenre/{g}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> getGameByGenre(@PathParam("g") String g) {
		
		System.out.print("id du genre :"+g+"\n");
		DaoFactory fact = DaoFactory.getInstance();
		dao.GameIsOfGenreDao gameIsOfGenreDao = fact.getGameIsOfGenreDao();
        
		
		List<Game> lg = gameIsOfGenreDao.findGamesByGenre(Integer.parseInt(g));
		
		System.out.print("premier jeu trouve :"+lg.get(0).getTitleGame()+"\n");
		return lg;

		
	}
	
	@GET
	@Path("/gameByConsole/{c}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> getGameByConsole(@PathParam("c") String c) {
		
		System.out.print("id du genre :"+c+"\n");
		DaoFactory fact = DaoFactory.getInstance();
		dao.GameIsOnConsoleDao gameIsOnConsoleDao = fact.getGameIsOnConsoleDao();
        
		
		List<Game> lg = gameIsOnConsoleDao.findGamesByConsole(Integer.parseInt(c));
		
		System.out.print("premier jeu trouve :"+lg.get(0).getTitleGame()+"\n");
		return lg;

		
	}
	
	@GET
	@Path("/ownedBy/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> printGameOwnedBy(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
		List<Game> g = gameDao.findAllUserGame(i);
		return g;	
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> printAllGames() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
        List<Game> u = gameDao.findAll();
		
		return u;
	}
	
	
	
	
	
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(Game game) throws URISyntaxException
	{
		System.out.print("passe GAME REST ************\n");
		System.out.println(game);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
        int idGame=0;
        try {
        	idGame = gameDao.create(game);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+game+" créé";
		
		
		//ajouts dans game_is_of_genre
		
		
        dao.GameIsOfGenreDao gameIsOfGenreDao = fact.getGameIsOfGenreDao();
        try {        	
        	gameIsOfGenreDao.createSeveral(idGame, game.getListOfGenreId());
        	//gameDao.create(game);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
        
		//ajouts dans game_is_on_console
        dao.GameIsOnConsoleDao gameIsOnConsoleDao = fact.getGameIsOnConsoleDao();
        try {        	
        	gameIsOnConsoleDao.createSeveral(idGame, game.getListOfConsoleId());
        	//gameDao.create(game);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
        
        return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/receiveGenres")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJsonGenre(Game game) throws URISyntaxException
	{
		System.out.print("passe GAME RECEIVE GENRE ************\n");
		System.out.println(game);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameIsOfGenreDao gameIsOfGenreDao = fact.getGameIsOfGenreDao();
		
        try {
        	
        	gameIsOfGenreDao.createSeveral(game.getIdGame(), game.getListOfGenreId());
        	//gameDao.create(game);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+game+" créé";
		return Response.status(200).entity(output).build();
	}
	
	

}