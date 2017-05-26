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
import bean.User;
import dao.DaoFactory;

@Path("user")
public class UserRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public User printGameById(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        User u = userDao.find(i);
		
		return u;			

	}

	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<User> printAllUsers() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        List<User> u = userDao.findAll();
		
		return u;			

	}
	
	@GET
	@Path("/getUsers/{l}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<User> printGameById(@PathParam("l") String l) {
		
		System.out.print("login :"+l+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        List<User> listOfUser = userDao.findByLogin(l);
		
		return listOfUser;			

	}
	
	
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(User user) throws URISyntaxException
	{
		System.out.print("passe USER REST ************\n");
		System.out.println(user);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        try {
        	userDao.create(user);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+user+" créé";
		return Response.status(200).entity(output).build();
	}



}