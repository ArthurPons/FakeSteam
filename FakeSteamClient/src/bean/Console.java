package bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
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
@ManagedBean(name = "Console")
public class Console implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idConsole;

	private String nameConsole;
	
	private String typeConsole;
	
	@JsonIgnore
	private List<Console> listOfAllConsoles;

	
	@JsonIgnore
	private Console firstConsole;
	@JsonIgnore
	private Console secondConsole;
	@JsonIgnore
	private Console thirdConsole;
	
	

	public Console() {
	}

	
	public Console getFirstConsole() {
		if(firstConsole==null)
		{			
			getLastThreeConsole();			
		}
		return firstConsole;
	}


	public void setFirstConsole(Console firstConsole) {
		
		this.firstConsole = firstConsole;
	}


	public Console getSecondConsole() {
		if(secondConsole==null)
		{			
			getLastThreeConsole();			
		}
		return secondConsole;
	}


	public void setSecondConsole(Console secondConsole) {
		this.secondConsole = secondConsole;
	}


	public Console getThirdConsole() {
		if(thirdConsole==null)
		{			
			getLastThreeConsole();			
		}
		return thirdConsole;
	}


	public void setThirdConsole(Console thirdConsole) {
		this.thirdConsole = thirdConsole;
	}
	
	public String getTypeConsole() {
		return typeConsole;
	}


	public void setTypeConsole(String typeConsole) {
		this.typeConsole = typeConsole;
	}


	public void getLastThreeConsole()
	{
		findListConsoles();
		int sizeList = listOfAllConsoles.size();
		firstConsole=listOfAllConsoles.get(sizeList-1);
		secondConsole=listOfAllConsoles.get(sizeList-2);
		thirdConsole=listOfAllConsoles.get(sizeList-3);
	}
		
	
	public void findListConsoles()
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/console/get");
		
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		listOfAllConsoles = null;
		try {
			listOfAllConsoles = mapper.readValue(jsonString, new TypeReference<List<Console>>(){});
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
	}
	
	
	public List<Console> getListOfAllConsoles() {	
		findListConsoles();
		return listOfAllConsoles;				
	}


	public void setListOfAllConsoles(List<Console> listOfAllConsoles) {
		this.listOfAllConsoles = listOfAllConsoles;
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

	public void submit() {	  	       
		
		
		//ajout dans la table Console
		//System.out.println("Submitted NameGenre : "+ nameConsole +"\n");

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