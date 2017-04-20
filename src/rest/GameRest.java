package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.SerializationFeature;


import bean.Game;

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
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> printAllGames() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
        List<Game> u = gameDao.findAll();
		
		return u;
	}

}