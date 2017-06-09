package rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public List<User> printGameById(@PathParam("i") int i) {
		
		//System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        List<User> listUser = new ArrayList<User>();        
        User u = userDao.find(i);
        
        listUser.add(u);
        listUser.add(new User());
        
		return listUser;			

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
	@Path("/getRating/{s}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Integer> getRating(@PathParam("s") String s) {		
		
		
		
		//regex pour retrouver l'id du user et du jeux (userid-gameid)
		String regex = "(.*)-(.*)";
		Pattern p = Pattern.compile(regex);
		
		Matcher m = p.matcher(s);
		
		m.find();
		int userId = Integer.parseInt(m.group(1));
		int gameId = Integer.parseInt(m.group(2));
		
		System.out.print("passe rest rating\n");
		DaoFactory fact = DaoFactory.getInstance();
		dao.RatingDao ratingDao = fact.getRatingDao();
		
        int r = ratingDao.findUserRating(userId,gameId);
		System.out.print("note  trouve : "+r+"\n");
        List<Integer> listRating = new ArrayList<Integer>();
        listRating.add(r);
        listRating.add((Integer) 0);
        
		return listRating;			

	}
	
	
	@GET
	@Path("/getUsers/{l}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<User> printGameById(@PathParam("l") String l) {
		
		//System.out.print("login :"+l+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        List<User> listOfUser = userDao.findByLogin(l);
		
		return listOfUser;			

	}
	
	@POST
	@Path("/buyGame")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buyGame(User user) throws URISyntaxException
	{
		//System.out.print("passe buy game ************\n");
		//System.out.println(user);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserOwnsGameDao userOwnsGameDao = fact.getUserOwnsGameDao();
		
        try {
        	userOwnsGameDao.create(user);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+user+" créé";
		return Response.status(200).entity(output).build();
	}
	
	
	@POST
	@Path("/addRating")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRating(User user) throws URISyntaxException
	{
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.RatingDao ratingDao = fact.getRatingDao();
		
        try {
        	ratingDao.create(user);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+user+" créé";
		return Response.status(200).entity(output).build();
	}
	
	
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(User user) throws URISyntaxException
	{
		//System.out.print("passe USER REST ************\n");
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