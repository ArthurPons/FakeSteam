package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Game;
import dao.DaoFactory;
import dao.GameDao;


public class FormGame extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_GAME         = "game";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/formGame.jsp";

    private GameDao    gameDao;

    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
    	System.out.print("passe init\n");
    	DaoFactory fact = DaoFactory.getInstance();
        this.gameDao = fact.getGameDao();
    }


    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	System.out.print("passe get\n");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
    	System.out.print("passe formulaire\n");
    	DbFormGame form = new DbFormGame( gameDao );

        /* Traitement de la requête et récupération du bean en résultant */
    	 form.addGame( request );
    	 //gameDao.create(game);

        /* Stockage du formulaire et du bean dans l'objet request */
        //request.setAttribute( ATT_FORM, form );
        //request.setAttribute( ATT_GAME, game );

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}