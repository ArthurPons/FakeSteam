package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Game;
import bean.User;
import dao.DaoFactory;
import dao.GameDao;
import dao.UserDao;


public class FormUser extends HttpServlet {
	
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_GAME         = "game";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/formUser.jsp";

    private UserDao userDao;

    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
    	System.out.print("passe init user\n");
    	DaoFactory fact = DaoFactory.getInstance();
        this.userDao = fact.getUserDao();
    }


    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	System.out.print("passe get\n");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
    	System.out.print("passe formulaire\n");
    	DbFormUser form = new DbFormUser( userDao );

        /* Traitement de la requête et récupération du bean en résultant */
    	form.addUser(request);
    	    	 
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}