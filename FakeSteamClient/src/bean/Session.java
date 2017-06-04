package bean;

import java.util.List;

import javax.faces.bean.SessionScoped;

@SessionScoped
public class Session {	
	private String username;
    
	private List<Game> panier; 
    
    
    public int login()
    {
    	return 0;
    }
    
    public int logout()
    {
    	return 0;
    }
}
