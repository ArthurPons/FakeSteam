package bean;

import java.io.IOException;
import java.io.Serializable;

import javax.json.JsonArray;
import javax.persistence.*;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;



public class Console implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idConsole;

	private String nameConsole;

	private int yearConsole;

	private List<Game> games;
	
	private List<Console> lastThreeConsoles;

	public List<Console> getLastThreeConsoles() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/console/get");
//		Client client = ClientBuilder.newClient();
//		Target target = client.target("http://localhost:8080/FakeSteam/rest/game/get"); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		List<Console> listAllConsoles = null;
		try {
			listAllConsoles = mapper.readValue(jsonString, new TypeReference<List<Genre>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (listAllConsoles.size() <= 3) {
			return listAllConsoles;
		} 
		else {
			return listAllConsoles.subList(listAllConsoles.size()-4, listAllConsoles.size()-1);
		}
	}

	public void setLastThreeConsoles(List<Console> lastThreeConsoles) {
		this.lastThreeConsoles = lastThreeConsoles;
	}

	public Console() {
	}

	public int getIdConsole() {
		return this.idConsole;
	}

	public void setIdConsole(int idConsole) {
		this.idConsole = idConsole;
	}

	public String getNameConsole() {
		return this.nameConsole;
	}

	public void setNameConsole(String nameConsole) {
		this.nameConsole = nameConsole;
	}

	public int getYearConsole() {
		return this.yearConsole;
	}

	public void setYearConsole(int yearConsole) {
		this.yearConsole = yearConsole;
	}

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}