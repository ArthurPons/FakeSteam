package bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArray;
import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Target;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.primefaces.model.UploadedFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import servlets.FormGame;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Game")
@ViewScoped
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
		

	private int idGame;	
	private String pictureUrlGame;	
	private float priceGame;	
	private String titleGame;

	private float rate;
	private int nbRating;
		
	private UploadedFile uploadedFile;	

	//@JsonIgnore
	private Rating rating;
	
	@JsonIgnore
	private List<String> listOfGenreName;
	@JsonIgnore
	private List<String> listOfConsoleName;
	@JsonIgnore
	private List<Game> listOfGameByGenre;
	@JsonIgnore
	private List<Game> listOfGameByConsole;
	

	private List<Comment> comments;
	private List<GameIsOfGenre> gameIsOfGenres;
	private List<Console> consoles;
	private List<Historic> historics;	
	private List<UserOwnsGame> userOwnsGames;
	
	@JsonIgnore
	private Game firstGameCarroussel;
	@JsonIgnore
	private Game secondGameCarroussel;
	@JsonIgnore
	private Game thirdGameCarroussel;
	
	private List<Integer> listOfGenreId;
	private List<Integer> listOfConsoleId;
	
	@JsonIgnore
	private int tempGenreId;
	
	@JsonIgnore
	private int tempConsoleId;
	

	private String pictureUrlGameToPrint;

	
	
	
	public void setPictureUrlGameToPrint(String pictureUrlGameToPrint) {
		this.pictureUrlGameToPrint = pictureUrlGameToPrint;
	}

	public int getTempConsoleId() {
		return tempConsoleId;
	}

	public void setTempConsoleId(int tempConsoleId) {
		this.tempConsoleId = tempConsoleId;
	}

	public int getTempGenreId() {
		return tempGenreId;
	}

	public void setTempGenreId(int tempGenreId) {
		this.tempGenreId = tempGenreId;
	}

	public List<Game> getListOfGameByConsole() {
		System.out.print("passe getlistofgamebyconsole\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/gameByConsole/"+tempConsoleId);
		
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			listOfGameByConsole = mapper.readValue(jsonString, new TypeReference<List<Game>>(){});
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
		
		
	
		
		return listOfGameByConsole;
	}
	
	public List<Game> getListOfGameByGenre() {
		//recuperer liste
		
		System.out.print("passe getlistofgamebygenre\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/gameByGenre/"+tempGenreId);
		
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			listOfGameByGenre = mapper.readValue(jsonString, new TypeReference<List<Game>>(){});
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
		
		
	
		
		return listOfGameByGenre;
	}

	public void setListOfGameByGenre(List<Game> listOfGameByGenre) {
		this.listOfGameByGenre = listOfGameByGenre;
	}

	public int getNbRating() {
		return nbRating;
	}

	public void setNbRating(int nbRating) {
		this.nbRating = nbRating;
	}

	public float getRate() {
		
		if ((idGame!=0) && (rate==0.0))
		{
			getTotalRatingGame();
			
		}
		
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Game getFirstGameCarroussel() {
		//System.out.print("passe premier car\n");
		if(firstGameCarroussel==null)
		{
			getLastThreeGames();
		}
		return firstGameCarroussel;
	}

	public void setFirstGameCarroussel(Game firstGameCarroussel) {
		this.firstGameCarroussel = firstGameCarroussel;
	}

	public Game getSecondGameCarroussel() {
		if(secondGameCarroussel==null)
		{
			getLastThreeGames();
		}
		return secondGameCarroussel;
	}

	public void setSecondGameCarroussel(Game secondGameCarroussel) {
		this.secondGameCarroussel = secondGameCarroussel;
	}

	public Game getThirdGameCarroussel() {
		if(thirdGameCarroussel==null)
		{
			getLastThreeGames();
		}
		return thirdGameCarroussel;
	}

	public void setThirdGameCarroussel(Game thirdeGameCarroussel) {
		this.thirdGameCarroussel = thirdeGameCarroussel;
	}

	public List<Integer> getListOfConsoleId() {
		return listOfConsoleId;
	}

	public void setListOfConsoleId(List<Integer> listOfConsoleId) {
		this.listOfConsoleId = listOfConsoleId;
	}

	public List<Integer> getListOfGenreId() {
		return listOfGenreId;
	}

	public void setListOfGenreId(List<Integer> listOfGenreId) {
		this.listOfGenreId = listOfGenreId;
	}

	public void setListOfGenreName(List<String> listOfGenreName) {
		this.listOfGenreName = listOfGenreName;
	}
	
	
	public void getTotalRatingGame() {
		
		Client client = ClientBuilder.newClient();	
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/getRating/"+idGame); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		List<Integer> listInt = new ArrayList<Integer>();
		
		try {
			
			listInt = mapper.readValue(jsonString, new TypeReference<List<Integer>>(){});
			int total=0;
			int cpt=0;
			float moyenne=0;
			//parcourir liste de rating
			for(Integer i:listInt)
			{
				total=total + i;
				System.out.print("note :"+i+"\n");
				cpt++;
			}
			
			moyenne = (float) total/cpt;
			//arrondi a un chiffre apres la virgule
			moyenne =  (float) Math.round(moyenne * 10) / 10;
			
			System.out.print("total :"+total+"\n");
			System.out.print("moyenne :"+moyenne+"\n");
			System.out.print("cpt :"+cpt+"\n");
			
			rate=moyenne;
			
			nbRating=cpt;
			
			
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
	
	public void findGameById()
	{
		//System.out.print("recherche du jeu, grace a son id.\n");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/get/"+idGame); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		Game g = new Game();
		
		try {
			List<Game> lg = mapper.readValue(jsonString, new TypeReference<List<Game>>(){});
			g=lg.get(0);
			
			titleGame=g.getTitleGame();
			//String path= System.getProperty("user.home")+"/images";
			pictureUrlGame = g.getPictureUrlGame();
			//pictureUrlGame=  System.getProperty("user.home")+"\\images\\"+g.getPictureUrlGame();
			priceGame=g.getPriceGame();
			
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
			
		
	}

	public List<String> getListOfGenreName() {
		//System.out.print("passe list genre\n");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/gameIsOfGenre/get/"+idGame); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("jsongenre :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			listOfGenreName = mapper.readValue(jsonString, new TypeReference<List<String>>(){});
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
				
		return listOfGenreName;
	}

	public void setListOfConsoleName(List<String> listOfGenre) {
		this.listOfGenreName = listOfGenre;
	}

	
	
	public List<String > getListOfConsoleName() {		
		
		System.out.print("passe list genre\n");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/gameIsOnConsole/get/"+idGame); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		System.out.print("jsonconsole :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			listOfConsoleName = mapper.readValue(jsonString, new TypeReference<List<String>>(){});
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
				
		
		return listOfConsoleName;
	}

	public void setListOfConsole(List<String > listOfConsole) {
		this.listOfConsoleName = listOfConsole;
	}

	public int getLastThreeGames() {
		
		//System.out.print("passe last three games\n");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/get"); 
		JsonArray json = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class); 
		String jsonString = json.toString();
		//System.out.print("json :"+json+"\n");
		ObjectMapper mapper = new ObjectMapper();
		
		List<Game> listAllGames = null;
		try {
			listAllGames = mapper.readValue(jsonString, new TypeReference<List<Game>>(){});
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
		
		int sizeList = listAllGames.size();
		firstGameCarroussel = listAllGames.get(sizeList-1);
		secondGameCarroussel = listAllGames.get(sizeList-2);
		thirdGameCarroussel = listAllGames.get(sizeList-3);
		
		return 0;
		
	}


	@JsonIgnore
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	

	public int getIdGame() {
		return this.idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getPictureUrlGameToPrint()
	{
		if ((idGame != 0) && (pictureUrlGame==null))
		{
			System.out.print("passPicture\n");
			findGameById();
		}
		System.out.print("image dans la base :"+pictureUrlGame+"\n");
		if ((pictureUrlGame.indexOf('/')==-1) && (pictureUrlGame.indexOf('\\')==-1))
		{
			System.out.print("picture url game :"+pictureUrlGame+"\n");
			pictureUrlGameToPrint = System.getProperty("user.home")+"\\images\\"+pictureUrlGame;
		}
		return pictureUrlGameToPrint;
	}
	
	public String getPictureUrlGame() {
		if ((idGame != 0) && (pictureUrlGame==null))
		{
			//System.out.print("passPicture\n");
			findGameById();
		}
		
		
		System.out.print("getpictureUrlGame "+pictureUrlGame+"\n");
		return this.pictureUrlGame;
	}

	public void setPictureUrlGame(String pictureUrlGame) {
		this.pictureUrlGame = pictureUrlGame;
	}

	public float getPriceGame() {
		if ((idGame != 0) && (((Float) priceGame) == null) )
		{
			//System.out.print("passPrice\n");
			findGameById();
		}
		return this.priceGame;
	}

	public void setPriceGame(float priceGame) {
		this.priceGame = priceGame;
	}

	public String getTitleGame() {
		if ((idGame != 0) && (titleGame==null))
		{
			//System.out.print("passtitle\n");
			//System.out.print("valeur de idGame :"+idGame+"\n");
			findGameById();
		}
		return this.titleGame;
	}

	public void setTitleGame(String titleGame) {
		this.titleGame = titleGame;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setGame(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setGame(null);

		return comment;
	}

	public List<GameIsOfGenre> getGameIsOfGenres() {
		return this.gameIsOfGenres;
	}

	public void setGameIsOfGenres(List<GameIsOfGenre> gameIsOfGenres) {
		this.gameIsOfGenres = gameIsOfGenres;
	}

	public GameIsOfGenre addGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().add(gameIsOfGenre);
		gameIsOfGenre.setGame(this);

		return gameIsOfGenre;
	}

	public GameIsOfGenre removeGameIsOfGenre(GameIsOfGenre gameIsOfGenre) {
		getGameIsOfGenres().remove(gameIsOfGenre);
		gameIsOfGenre.setGame(null);

		return gameIsOfGenre;
	}

	public List<Console> getConsoles() {
		return this.consoles;
	}

	public void setConsoles(List<Console> consoles) {
		this.consoles = consoles;
	}

	public List<Historic> getHistorics() {
		return this.historics;
	}

	public void setHistorics(List<Historic> historics) {
		this.historics = historics;
	}

	public Historic addHistoric(Historic historic) {
		getHistorics().add(historic);
		historic.setGame(this);

		return historic;
	}

	public Historic removeHistoric(Historic historic) {
		getHistorics().remove(historic);
		historic.setGame(null);

		return historic;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public List<UserOwnsGame> getUserOwnsGames() {
		return this.userOwnsGames;
	}

	public void setUserOwnsGames(List<UserOwnsGame> userOwnsGames) {
		this.userOwnsGames = userOwnsGames;
	}

	public UserOwnsGame addUserOwnsGame(UserOwnsGame userOwnsGame) {
		getUserOwnsGames().add(userOwnsGame);
		userOwnsGame.setGame(this);

		return userOwnsGame;
	}

	public UserOwnsGame removeUserOwnsGame(UserOwnsGame userOwnsGame) {
		getUserOwnsGames().remove(userOwnsGame);
		userOwnsGame.setGame(null);

		return userOwnsGame;
	}
	
	
	//check if the file is unique (return new name if the name is already taken)
		public String uniqueFile (String path, String filename)    throws IOException
			  {
			
				//System.out.print("nom du fichier :"+filename+"\n");
				//le chemin ne doit contenir que des slash
				path = path.replace("\\", "/");
				//System.out.print("chemin :"+path+"\n");
				Pattern p = Pattern.compile("([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+)");
				Matcher m = p.matcher(filename);
				m.find();
				
				String prefix = m.group(1);
				//System.out.print("prefix :"+prefix+"\n");
				
				String extension = m.group(2);
				//System.out.print("extension :"+extension+"\n");
				
				
				File file = new File(path+"/"+ prefix+"."+extension);
				//System.out.print("chemin absolu :"+path+"/"+ prefix+"."+extension+"\n");
				
				boolean wasCreated = false;
				int i=1;
				while(!wasCreated)
				{
					if(file.exists())
					{
						//System.out.print("le fichier existe !");
						i++;
						//le fichier existe deja
						file = new File(path+"/"+ prefix+i+"."+extension);
						
					}
					else
					{
						//le fichier n'existe pas, on le renvoie
						if(i==1)
						{
							return prefix+"."+extension;
						}
						else
						{
							return prefix+i+"."+extension;
						}
						
					}
				}
				
				
				
			    return null;
			    
		}

		
		
	public void submit() {
         
		  
        
        //upload de l'image
		if(uploadedFile != null) {
			String fileName = uploadedFile.getFileName();
			
			
			//System.out.print("nom de l'image :"+fileName+"\n");
						
			//String path = FormGame.contextPath;
			String path= System.getProperty("user.home")+"/images";
			
			//System.out.print("chemin de l'image* :"+path);
			
			
			//cree repertoire si pas deja cree
			if (!(new File(path)).exists())
			{
				//System.out.print("Creation de  "+path+"\n");
				new File(path).mkdir();
			}
			else
			{
				//System.out.print("le repertoire "+path+" existe deja\n");
			}
			
			try {
				//verifie si le nom du fichier n'existe pas deja. Sinon, incremente le nom du fichier
				String uniqueNameFile = uniqueFile(path+"/images", fileName);
				
				System.out.print("nom du fichier final :"+uniqueNameFile+"\n");
				InputStream in = uploadedFile.getInputstream();
				OutputStream out = new FileOutputStream(new File(path,uniqueNameFile) );
				int read = 0;
	            byte[] bytes = new byte[1024];
	          
	            while ((read = in.read(bytes)) != -1) {
	                out.write(bytes, 0, read);
	            }
	            in.close();
                out.flush();
                out.close();
                
                //System.out.println("New file created!");
                pictureUrlGame= uniqueNameFile;
                System.out.print("image dans submit"+pictureUrlGame+"\n");
                
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			
        }
		else
		{
			System.out.print("image null\n");
		}
		
		//traitement liste de genres
		if (listOfGenreId != null)
		{
			//System.out.print("liste de genre non null:\n");
			//System.out.print("premier element :"+listOfGenreId.get(0)+"\n");
			
		}
		else
		{
			System.out.print("liste de genre null\n");
		}
		
		//ajout dans la table Game
		//System.out.println("Submitted titleGame : "+ titleGame +"\n");
        //System.out.println("Submitted priceGame : "+ priceGame +"\n");   
        //System.out.println("Submitted pictureUrlGame : "+ pictureUrlGame +"\n"); 
        
        
        //ajout du jeu dans Game
        try {
        	
			ResteasyClient client = new ResteasyClientBuilder().build();

			ResteasyWebTarget target = client.target("http://localhost:8080/FakeSteam/rest/game/receive");
			
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
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                   
    }
}
