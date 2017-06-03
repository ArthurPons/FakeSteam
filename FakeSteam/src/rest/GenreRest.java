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

import bean.Genre;


import dao.DaoFactory;

@Path("genre")
public class GenreRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Genre printGenreByd(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.GenreDao genreDao = fact.getGenreDao();
		
		Genre g = genreDao.find(i);
		return g;
		
	}	

	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Genre> printAllGenre() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GenreDao genreDao = fact.getGenreDao();
		
        List<Genre> g = genreDao.findAll();
		
		return g;
	}
	
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(Genre genre) throws URISyntaxException
	{
		System.out.print("passe GAME REST ************\n");
		System.out.println(genre);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.GenreDao genreDao = fact.getGenreDao();
		
        try {
        	genreDao.create(genre);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+genre+" crée";
		return Response.status(200).entity(output).build();
	}

}