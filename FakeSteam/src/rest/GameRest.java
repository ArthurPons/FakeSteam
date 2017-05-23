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

@Path("game")
public class GameRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Game printGameById(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
		Game g = gameDao.find(i);
		return g;

		
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
		
        try {
        	gameDao.create(game);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+game+" créé";
		return Response.status(200).entity(output).build();
	}

}