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
<<<<<<< HEAD
import bean.User;
=======
>>>>>>> b7148432fc5e4c48c627a473912a1fece9e1e616
import dao.DaoFactory;

@Path("Game")
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
<<<<<<< HEAD
		
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Game> printGameById() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameDao gameDao = fact.getGameDao();
		
        List<Game> u = gameDao.findAll();
		
		return u;
			
=======
		/*
		List<Game> listOfGames = new ArrayList<Game>();
		
		Game g = new Game();
		g.setPictureUrlGame("image.com");
		g.setPriceGame((float) 49.99);
		g.setIdGame(1);
		g.setTitleGame("Dark Souls");
		
		Game g2 = new Game();
		g2.setPictureUrlGame("planete.pouf");
		g2.setPriceGame((float) 13.23);
		g2.setIdGame(1);
		g2.setTitleGame("Dragon's Lair");
		
		
		listOfGames.add(g);
		listOfGames.add(g2);
		*/
		
		
>>>>>>> b7148432fc5e4c48c627a473912a1fece9e1e616

	}

}