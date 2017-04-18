package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.SerializationFeature;

import bean.User;
import dao.DaoFactory;

@Path("User")
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
	public List<User> printGameById() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.UserDao userDao = fact.getUserDao();
		
        List<User> u = userDao.findAll();
		
		return u;
			

	}
	
	

}