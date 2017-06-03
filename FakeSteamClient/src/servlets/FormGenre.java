package servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FormGenre extends HttpServlet {
	
	public static final String VUE = "/formGenre.xhtml";
    
	public static String contextPath;
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	System.out.print("passe form genre\n");
    	
    	
    	
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}