package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.faces.bean.ManagedBean;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/*
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
*/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "UserClient")
public class UserClient {
	
	private int idUser;
	private List<bean.Game> listOfGame;

	public int getIdUser() {
		return idUser;
	}

	public List<bean.Game> getListOfGame() {
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		System.out.print("idutilisateur :"+idUser+"\n");
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/ownedBy/2"); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		List<bean.Game> listOfGame = null;
		try {
			listOfGame = mapper.readValue(jsonString, new TypeReference<List<bean.Game>>(){});
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
		
		
		return listOfGame;
	}

	public void setListOfGame(List<bean.Game> listOfGame) {
		this.listOfGame = listOfGame;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


}
