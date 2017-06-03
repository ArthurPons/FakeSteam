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

import bean.Console;
import dao.DaoFactory;

@Path("console")
public class ConsoleRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Console printConsoleByid(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.ConsoleDao consoleDao = fact.getConsoleDao();
		
		Console c = consoleDao.find(i);
		return c;
		
	}	

	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Console> printAllConsole() {
		
		
		DaoFactory fact = DaoFactory.getInstance();
		dao.ConsoleDao consoleDao = fact.getConsoleDao();
		
        List<Console> c = consoleDao.findAll();
		
		return c;
	}
	
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(Console console) throws URISyntaxException
	{
		System.out.print("passe CONSOLE REST ************\n");
		System.out.println(console);
		
		DaoFactory fact = DaoFactory.getInstance();
		dao.ConsoleDao consoleDao = fact.getConsoleDao();
		
        try {
        	consoleDao.create(console);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+console+" crée";
		return Response.status(200).entity(output).build();
	}

}