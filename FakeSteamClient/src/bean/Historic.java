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

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Historic")
public class Historic implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idHistoric;
	private Date dateHistoric;	
	private int fk_game;
	private int fk_user;

	public int getFk_user() {
		return fk_user;
	}

	public int getFk_game() {
		return fk_game;
	}

	public void setFk_game(int fk_game) {
		this.fk_game = fk_game;
	}

	public void setFk_user(int fk_user) {
		this.fk_user = fk_user;
	}

	private Game game;

	private User user;

	public Historic() {
	}

	public int getIdHistoric() {
		return this.idHistoric;
	}

	public void setIdHistoric(int idHistoric) {
		this.idHistoric = idHistoric;
	}

	public Date getDateHistoric() {
		return this.dateHistoric;
	}

	public void setDateHistoric(Date dateHistoric) {
		this.dateHistoric = dateHistoric;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getGameId()
	{
		return fk_game;
	}

	public int getUserId()
	{
		return fk_user;
	}
	
	public void submit() {
        
		//calcul de la date courante
		
		dateHistoric = new Date();
		
		System.out.println("Submitted dateHistoric : "+ dateHistoric.toString());
        System.out.println("Submitted fk_game : "+ fk_game);   
        System.out.println("Submitted fk_user : "+ fk_user);   
    

        
        try {
        	
			ResteasyClient client = new ResteasyClientBuilder().build();

			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/historic/receive");
			
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