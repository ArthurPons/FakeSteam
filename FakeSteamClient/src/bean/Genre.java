package bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Genre")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idGenre;


	private String nameGenre;

	private List<GameIsOfGenre> gameIsOfGenres;
	
	private List<Genre> lastThreeGenres;

	public List<Genre> getLastThreeGenres() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/genre/get");
//		Client client = ClientBuilder.newClient();
//		Target target = client.target("http://localhost:8080/FakeSteam/rest/game/get"); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		List<Genre> listAllGenres = null;
		try {
			listAllGenres = mapper.readValue(jsonString, new TypeReference<List<Genre>>(){});
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
		
		lastThreeGenres = listAllGenres.subList(listAllGenres.size()-4, listAllGenres.size()-1);

		return lastThreeGenres;
	}

	public void setLastThreeGenres(List<Genre> lastThreeGenres) {
		this.lastThreeGenres = lastThreeGenres;
	}

	public Genre() {
	}

	public int getIdGenre() {
		return this.idGenre;
	}

	public void setIdGenre(int idGenre) {
		this.idGenre = idGenre;
	}

	public String getNameGenre() {
		return this.nameGenre;
	}

	public void setNameGenre(String nameGenre) {
		this.nameGenre = nameGenre;
	}

	public List<GameIsOfGenre> getGameIsOfGenres() {
		return this.gameIsOfGenres;
	}

	public void setGameIsOfGenres(List<GameIsOfGenre> gameIsOfGenres) {
		this.gameIsOfGenres = gameIsOfGenres;
	}

	public GameIsOfGenre addGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().add(gameIsOfGenre);
		gameIsOfGenre.setGenre(this);

		return gameIsOfGenre;
	}

	public GameIsOfGenre removeGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().remove(gameIsOfGenre);
		gameIsOfGenre.setGenre(null);

		return gameIsOfGenre;
	}
	
public void submit() {	  
       
		
				
		//ajout dans la table Genre
		System.out.println("Submitted NameGenre : "+ nameGenre +"\n");

        try {
        	
			ResteasyClient client = new ResteasyClientBuilder().build();

			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/genre/receive");
			
			Response response = target.request().post(Entity.entity(this,MediaType.APPLICATION_JSON));			
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}

			System.out.println("Server response : \n");
			System.out.println(response.readEntity(String.class));

			response.close();

		} catch (Exception e) {

			e.printStackTrace();

		} 
        
        
        
        
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("sucess.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                   
    }
	
	

}
