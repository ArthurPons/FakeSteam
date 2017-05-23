package servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;



public class FormLogUser extends HttpServlet {
	
	/*
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_GAME         = "game";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/formLogUser.jsp";

    private UserDao userDao;

    
    public void init() throws ServletException {
        
    	DaoFactory fact = DaoFactory.getInstance();
        this.userDao = fact.getUserDao();
    }


    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
    	DbFormLogUser form = new DbFormLogUser( userDao );

        
    	try {
			Optional<User> loggedUser = form.authentificateUser(request);

			if (loggedUser.isPresent()) {
				System.out.println("User "+loggedUser.get().getIdUser()+" is logged");
			} else {
				System.out.println("Username or password not correct");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	    	 
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    */
}