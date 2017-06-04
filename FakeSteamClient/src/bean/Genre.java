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
import javax.json.JsonArray;
import javax.persistence.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Genre")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idGenre;


	private String nameGenre;
	
	@JsonIgnore
	private List<Genre> listOfAllGenres;

	public Genre() {
	}

	public List<Genre> getListOfAllGenres() {			
	
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/genre/get");
		
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		listOfAllGenres = null;
		try {
			listOfAllGenres = mapper.readValue(jsonString, new TypeReference<List<Genre>>(){});
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
		
		
		return listOfAllGenres;					
	}
	
	public void setListOfAllGenres(List<Genre> listOfAllGenres) {
		this.listOfAllGenres = listOfAllGenres;
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