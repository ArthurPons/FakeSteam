package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class GetInfos extends HttpServlet{

	public static final String VUE  = "/WEB-INF/getGames.jsp";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	System.out.print("passe get games\n");
    	System.out.print("passe servlet client\n");
    	List<String> listOfGame = new ArrayList<>();
		ResteasyClient client = new ResteasyClientBuilder().build();
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/get"); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		System.out.print(json);	
    	
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
	
	
	
}
