package bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.json.JsonArray;
import javax.persistence.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Rating")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int idRating;
	private int fkGameRating;
	private int fkUserRating;
	private double ratingRating;
	private Game game;
	private User user;

	public Rating() {
	}

	public int getIdRating() {
		return this.idRating;
	}

	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}

	public int getFkGameRating() {
		return this.fkGameRating;
	}

	public void setFkGameRating(int fkGameRating) {
		this.fkGameRating = fkGameRating;
	}

	public int getFkUserRating() {
		return this.fkUserRating;
	}

	public void setFkUserRating(int fkUserRating) {
		this.fkUserRating = fkUserRating;
	}

	public double getRatingRating() {
		return this.ratingRating;
	}

	public void setRatingRating(double ratingRating) {
		this.ratingRating = ratingRating;
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
		return fkGameRating;
	}

	public int getUserId()
	{
		return fkUserRating;
	}

	
	public Rating (int rating, int gameId, int userId)
	{
		ratingRating=rating;
		fkGameRating = gameId;
		fkUserRating = userId;
		
	}
	
	public void submit() {
        
		System.out.println("Submitted ratingRating : "+ ratingRating);
        System.out.println("Submitted fkGameRating : "+ fkGameRating);   
        System.out.println("Submitted fkUserRating : "+ fkUserRating);   
    

        
        try {
        	
			ResteasyClient client = new ResteasyClientBuilder().build();

			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/rating/receive");
			
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