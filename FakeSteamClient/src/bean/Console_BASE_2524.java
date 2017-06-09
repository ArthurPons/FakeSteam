package bean;

import java.io.IOException;
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
@ManagedBean(name = "Console")
public class Console implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idConsole;

	private String nameConsole;

	private String  typeConsole;

	private List<Game> games;

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

	public String getTypeConsole() {
		return typeConsole;
	}

	public void setTypeConsole(String typeConsole) {
		this.typeConsole = typeConsole;
	}

	public void setNameConsole(String nameConsole) {
		this.nameConsole = nameConsole;
	}


	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	public void submit() {	  
	       
		
		
		//ajout dans la table Console
		System.out.println("Submitted NameGenre : "+ nameConsole +"\n");
		System.out.println("Submitted TypeConsole : "+ typeConsole +"\n");

        try {
        	
			ResteasyClient client = new ResteasyClientBuilder().build();

			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/console/receive");
			
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