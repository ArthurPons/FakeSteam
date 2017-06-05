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
import bean.GameIsOfGenre;
import bean.User;
import dao.DaoFactory;

@Path("gameIsOfGenre")
public class GameIsOfGenreRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<String> getNameGenreByGameId(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.GameIsOfGenreDao gameIsOfGenre = fact.getGameIsOfGenreDao();
        
		
        List<String> l = gameIsOfGenre.find(i);       
     
		return l;
		
	}
		
	

}