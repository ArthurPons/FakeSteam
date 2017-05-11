package rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoFactory;

import bean.Rating;
import bean.User;


@Path("rating")
public class RatingRest {

	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(Rating rating) throws URISyntaxException
	{
		System.out.print("passe RATING REST ************\n");
		System.out.println(rating);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.RatingDao ratingDao = fact.getRatingDao();
		
        try {
        	ratingDao.create(rating);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+rating+" créé";
		return Response.status(200).entity(output).build();
	}
	
}
