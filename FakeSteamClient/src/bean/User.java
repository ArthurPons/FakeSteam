package bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.json.JsonArray;
import javax.persistence.*;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "User")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idUser;
	private String pwdUser;
	private byte[] saltUser;
	private String usernameUser;
	
	private Rating rating;
	private int tempRate;
	
	private List<Game> panier; 
	private float totalAmountPanier;
	
	private List<Comment> comments;
	private List<Historic> historics;
	
	private List<Game> listOfGame;
	
	private int tempGame;
	@JsonIgnore
	private int tempRating;
	
	@JsonIgnore
	private List<List<Object>> listHistoric;
	
	private String tempComment;
	
	private int isAdmin;
	
	
	
	
	public List<List<Object>> getListHistoric() {
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/getHistoric/"+idUser);
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		listHistoric = new ArrayList<List<Object>>();
		
		try {
			List<Historic> listOfHistory = mapper.readValue(jsonString, new TypeReference<List<Historic>>(){});
			
			for(Historic h :listOfHistory)
			{
				//date
				Date dateFromHistory = h.getDateHistoric();
				
				List<Object> sousList = new ArrayList<Object>();
				//id du jeu
				int gameIdFromHistory=h.getGameId();
				
				//recherche du jeu
				Game tempGame = new Game();
				tempGame = tempGame.findGameWithId(gameIdFromHistory);
				
				sousList.add(tempGame.getIdGame());
				sousList.add(tempGame.getTitleGame());
				sousList.add(dateFromHistory);
				listHistoric.add(sousList);			
				
				
			}			
			
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
		
		
		
		return listHistoric;
	}


	public void setListHistoric(List<List<Object>> listHistoric) {
		this.listHistoric = listHistoric;
	}


	public String getTempComment() {
		return tempComment;
	}
	
	
	public void setTempComment(String tempComment) {
		
		this.tempComment = tempComment;
		System.out.print("ajout du commentaire "+tempComment+".Valeur de tempGame :"+tempGame+"\n");
		if ((tempGame!=0) && (tempComment != null) )
		{
			try {
		      	
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/comment/receive");
					
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
		}	
		this.tempComment=null;
		
		
		
		
	}

	public int getIsAdmin() {
		
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setTempRating(int tempRating) {
		this.tempRating = tempRating;
	}

	public int getTempGame() {
		return tempGame;
	}

	public void setTempGame(int tempGame) {
		this.tempGame = tempGame;
	}

	public int getTempRate() {
		return tempRate;
	}

	public int getTempRating() {
		//recherche si l'utilisateur a deja mis une note pour ce jeu (si oui, renvoie la note, sinon renvoie -1 (pas 0, car 0 peut etre une note)
		
		if(tempGame!=0)
		{
			System.out.print("recherche de la note\n");
			Client client = ClientBuilder.newClient();			
			
			WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/getRating/"+idUser+"-"+tempGame); 
			JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
			String jsonString = json.toString();
			System.out.print("json :"+json+"\n");
			ObjectMapper mapper = new ObjectMapper();
			
			List<Integer> listInt = new ArrayList<Integer>();
			
			try {
				
				listInt = mapper.readValue(jsonString, new TypeReference<List<Integer>>(){});
				
				
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
			
			if(listInt==null)
			{
				tempRating = -1;
				return  -1;
			}
			else
			{
				System.out.print("note donne par le user :"+listInt.get(0)+"\n");
				tempRating = listInt.get(0);
				return listInt.get(0);
			}
			
			
			
			
		}
		return -1;
	}
	
	public void setTempRate(int tempRate) {
		//sauvegarde de la note dans Rating (pour que le user ne puisse pas noter deux fois le meme jeu)
		this.tempRate=tempRate;
		System.out.print("ajout de la note "+tempRate+"\n");
		System.out.print("id du jeu (rating)"+tempGame+"\n");
		
		if(tempGame!=0)
		{
			try {
		      	
				ResteasyClient client = new ResteasyClientBuilder().build();

				ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/addRating");
					
				Response response = target.request().post(Entity.entity(this,MediaType.APPLICATION_JSON));			
					
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
				}

				System.out.println("Server response : \n");
				System.out.println(response.readEntity(String.class));

				response.close();
				this.tempRate=0;
				
				

			} catch (Exception e) {

				e.printStackTrace();

			}
		}		
						
		
	}

	private String pwd_user;

	public String getPwd_user() {
		return pwd_user;
	}

	public void setPwd_user(String pwd_user) {
		this.pwd_user = pwd_user;
	}

	public List<Game> getPanier() {
		return panier;
	}

	public void setPanier(List<Game> panier) {
		this.panier = panier;
	}

	public User() {
		//initialisationdu prix du panier
		totalAmountPanier = 0;
		
	}

	
	public float getTotalAmountPanier() {
		return totalAmountPanier;
	}

	public void setTotalAmountPanier(float totalAmountPanier) {
		this.totalAmountPanier = totalAmountPanier;
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getPwdUser() {
		return this.pwdUser;
	}

	public void setPwdUser(String pwdUser) {
		this.pwdUser = pwdUser;
	}
	
	public byte[] getSaltUser() {
		return this.saltUser;
	}
	
	public void setSaltUser(byte[] salt) {
		this.saltUser = salt;
	}


	public String getUsernameUser() {
		return this.usernameUser;
	}

	public void setUsernameUser(String usernameUser) {
		this.usernameUser = usernameUser;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setUser(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setUser(null);

		return comment;
	}

	public List<Historic> getHistorics() {
		return this.historics;
	}

	public void setHistorics(List<Historic> historics) {
		this.historics = historics;
	}

	public Historic addHistoric(Historic historic) {
		getHistorics().add(historic);
		historic.setUser(this);

		return historic;
	}

	public Historic removeHistoric(Historic historic) {
		getHistorics().remove(historic);
		historic.setUser(null);

		return historic;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public int isCartEmpty()
	{
		if((panier != null) && (panier.size()!=0))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	//si user possede le jeu, renvoi le jeu. null sinon.
	public Game ownsGame(int idOfGame)
	{
		System.out.print("id du jeu a recherche:"+idOfGame+"\n");
		tempGame=idOfGame;
		
		getListOfGame();
		
		
		System.out.print("Jeu posse par le joueur\n");
		for(Game g:listOfGame)
		{
			System.out.print("nom du jeu possede : "+g.getTitleGame()+"\n");
		}
		
		for(Game g:listOfGame)
		{
			if(g.getIdGame()==idOfGame)
			{
				return g;
			}
		}
		
		return null;
	}
	

	public List<Game> getListOfGame() {
		
		System.out.print("recherche des jeux\n");
		Client client = ClientBuilder.newClient();	
		
		//avoir recuperer l'id du user
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/ownedBy/"+idUser); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			listOfGame = mapper.readValue(jsonString, new TypeReference<List<Game>>(){});
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

	public void setListOfGame(List<Game> listOfGame) {
		this.listOfGame = listOfGame;
	}

	private static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
    {
    	String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
    /*
     * Méthode utilitaire pour ajouter du sel à un mdp 
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    //recuperer l'objet id associe a l'id
    
    public User getUserFromId(int idOfUser)
    {
    	if(idOfUser!=0)
    	{
    		System.out.print("recherche du user, grace a son id (qui est "+idOfUser+"\n");
    		Client client = ClientBuilder.newClient();
    		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/get/"+idOfUser); 
    		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
    		String jsonString = json.toString();
    		System.out.print("json :"+json+"\n");
    		ObjectMapper mapper = new ObjectMapper();
    		
    		User u = new User();
    		
    		try {
    			List<User> lu = mapper.readValue(jsonString, new TypeReference<List<User>>(){});
    			u=lu.get(0);
    			
    			usernameUser=u.getUsernameUser();
    			isAdmin = u.getIsAdmin();
    			System.out.print("valeur de usernma** :"+usernameUser+"\n");
    			System.out.print("valeur de isAdmin :"+isAdmin+"\n");
    			
    			//stockage des attributs
    			
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
    		return u;
    	}
    	
		return null;
		
    }
	
    public void buyGamesInPanier()
    {    	
    	try {
    	      	
    			ResteasyClient client = new ResteasyClientBuilder().build();

    			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/buyGame");
    				
    			Response response = target.request().post(Entity.entity(this,MediaType.APPLICATION_JSON));			
    				
    			if (response.getStatus() != 200) {
    				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
    			}

    			System.out.println("Server response : \n");
    			System.out.println(response.readEntity(String.class));

    			response.close();
    			
    			//on vide le panier
    			panier=null;
    			
    			//on met le total a zero
    			totalAmountPanier=0;
    			
    			try {
    				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			

    		} catch (Exception e) {

    			e.printStackTrace();

    		} 
    	
    }
    
    public void addToPanier(Game gamePanier)
    {
    	System.out.print("tentative d'ajout au panier du jeu"+gamePanier.getTitleGame()+"\n");
    	int isAlreadyinPanier=0;
    	if (panier==null)
    	{
    		panier = new ArrayList<Game>();
    	}
    	for(Game g:panier)
    	{
    		if(g.getIdGame()== gamePanier.getIdGame())
    		{
    			isAlreadyinPanier=1;
    		}
    	}
    	
    	if(isAlreadyinPanier==0)
    	{
    		System.out.print("ajout du jeu "+gamePanier.getTitleGame()+" dans le panier\n");
    		panier.add(gamePanier);
    		totalAmountPanier = totalAmountPanier + gamePanier.getPriceGame();
    		System.out.print("total amount :"+totalAmountPanier+"\n");
    	}    	
    	
    }
	
 
    
    public String login(){
    	
    	//recuperation login/mdp du formulaire
    	System.out.print("passe user login\n");
    	System.out.println("Submitted username : "+ usernameUser +"\n");
        System.out.println("Submitted pwd : "+ pwdUser +"\n");   
        
        //recuperation du hash
        try {
			saltUser = getSalt();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/getUsers/"+usernameUser); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		List<User> listOfUser = null;
		
		try {
			listOfUser = mapper.readValue(jsonString, new TypeReference<List<User>>(){});
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
		
		
		for(User u : listOfUser)
		{
			//recuperer salt
			 byte[] saltUser = u.getSaltUser();
			 
			//generer hash, avec le mdp
			 String pwdHash = get_SHA_256_SecurePassword(pwdUser ,saltUser);
			 if((pwdHash).equals(u.getPwdUser()) )
			 {
				 idUser=u.getIdUser();
				 break;
			 }
			 
			//si pareil que le hash, alors utilisateur trouve
		}
		
		if(idUser==0)
		{
			System.out.print("utilisateur non trouve\n");
			return null;
		}
		else
		{
			System.out.print("utilisateur trouve. Id : "+idUser+"\n");//pas bon si 0
			getUserFromId(idUser);
			
			//demarre session 
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("user", this);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 return "userhome?faces-redirect=true";
			 
		}
		   	
    	
    }
    
    public String logout()
    {
    	System.out.print("logout\n");
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	//raffraichit la page
    	/*try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
        return "index?faces-redirect=true";
        
    }
    
	public void submit() {
	 		                
		System.out.println("Submitted idUser : "+ usernameUser +"\n");
        System.out.println("Submitted pwd : "+ pwdUser +"\n");   
        
        try {
			saltUser = getSalt();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String securePassword = get_SHA_256_SecurePassword(pwdUser, saltUser);
        pwdUser = securePassword;
        
        try {
        	
			ResteasyClient client = new ResteasyClientBuilder().build();

			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/user/receive");
			
			Response response = target.request().post(Entity.entity(this,MediaType.APPLICATION_JSON));			
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}

			System.out.println("Server response : \n");
			System.out.println(response.readEntity(String.class));

			response.close();
			//le compte du user a ete cree
			
			
			
			//sauvegarde dans user de l'id precedemment cree
			Client client2 = ClientBuilder.newClient();
	        WebTarget target2 = client2.target("http://localhost:8080/FakeSteam/rest/user/getUsers/"+usernameUser); 
			JsonArray json2 = target2.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
			String jsonString2 = json2.toString();
			ObjectMapper mapper2 = new ObjectMapper();
			List<User> listOfUser = null;
			
			try {
				listOfUser = mapper2.readValue(jsonString2, new TypeReference<List<User>>(){});
				idUser=listOfUser.get(0).getIdUser();
				
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
			
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//ouverture d'une session, et renvoie vers la page d'accueil
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("user", this);

		} catch (Exception e) {

			e.printStackTrace();

		}        
        
    }
}