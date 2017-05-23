package bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.primefaces.model.UploadedFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dao.DaoException;
import dao.DaoFactory;
import dao.GameDao;
import dao.UserDao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown=true)
@ManagedBean(name = "Game")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private int idGame;	
	private String pictureUrlGame;	
	private float priceGame;	
	private String titleGame;
		
	private UploadedFile uploadedFile;	

	//@JsonIgnore
	private Rating rating;
	
	private List<Comment> comments;
	private List<GameIsOfGenre> gameIsOfGenres;
	private List<Console> consoles;
	private List<Historic> historics;	
	private List<UserOwnsGame> userOwnsGames;

	@JsonIgnore
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public Game() {
	}

	public int getIdGame() {
		return this.idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getPictureUrlGame() {
		return this.pictureUrlGame;
	}

	public void setPictureUrlGame(String pictureUrlGame) {
		this.pictureUrlGame = pictureUrlGame;
	}

	public float getPriceGame() {
		return this.priceGame;
	}

	public void setPriceGame(float priceGame) {
		this.priceGame = priceGame;
	}

	public String getTitleGame() {
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
			
				System.out.print("nom du fichier :"+filename+"\n");
				//le chemin ne doit contenir que des slash
				path = path.replace("\\", "/");
				System.out.print("chemin :"+path+"\n");
				Pattern p = Pattern.compile("([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+)");
				Matcher m = p.matcher(filename);
				m.find();
				
				String prefix = m.group(1);
				System.out.print("prefix :"+prefix+"\n");
				
				String extension = m.group(2);
				System.out.print("extension :"+extension+"\n");
				
				
				File file = new File(path+"/"+ prefix+"."+extension);
				System.out.print("chemin absolu :"+path+"/"+ prefix+"."+extension+"\n");
				
				boolean wasCreated = false;
				int i=1;
				while(!wasCreated)
				{
					if(file.exists())
					{
						System.out.print("le fichier existe !");
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
			
			
			System.out.print("nom de l'image :"+fileName+"\n");
			System.out.print("Chemin du repertoire contenant les images :C:\\Users\\thomas\\Pictures\\FakeSteam \n");
			//String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			String path ="C:/Users/thomas/Pictures/FakeSteam/images";		
			
			
			//cree repertoire si pas deja cree
			if (!(new File(path)).exists())
			{
				System.out.print("Creation de  "+path+"\n");
				new File(path).mkdir();
			}
			else
			{
				System.out.print("le repertoire "+path+" existe deja\n");
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
                
                System.out.println("New file created!");
                pictureUrlGame= uniqueNameFile;
                
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
		
				
		//ajout dans la table Game
		System.out.println("Submitted titleGame : "+ titleGame +"\n");
        System.out.println("Submitted priceGame : "+ priceGame +"\n");   
        System.out.println("Submitted pictureUrlGame : "+ pictureUrlGame +"\n"); 
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
			FacesContext.getCurrentInstance().getExternalContext().redirect("sucess.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                   
    }
}