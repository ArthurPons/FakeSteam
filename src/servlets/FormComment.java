package servlets;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Comment;
import bean.Game;
import bean.User;
import dao.CommentDao;
import dao.DaoFactory;
import dao.GameDao;
import dao.UserDao;


public class FormComment extends HttpServlet {
	
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_GAME         = "game";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/resultComment.xhtml";

    private CommentDao commentDao;

    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
    	System.out.print("passe init comment\n");
    	DaoFactory fact = DaoFactory.getInstance();
        this.commentDao = fact.getCommentDao();
    }


    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	System.out.print("passe get\n");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
    	System.out.print("passe formulaire commentaire bis \n");
    	CheckFormComment form = new CheckFormComment( commentDao );

        /* Traitement de la requête et récupération du bean en résultant */
    	Comment comment = form.addComment(request);
    	    	 
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}