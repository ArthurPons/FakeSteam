package servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FormLogin extends HttpServlet {
    public static final String VUE = "/login.xhtml";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	System.out.print("passe get (historic)\n");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
    	System.out.print("passe post (historic)\n");    	    	 
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}