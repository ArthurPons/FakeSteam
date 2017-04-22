package servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import bean.Comment;
import bean.Game;
import bean.User;
import dao.CommentDao;
import dao.DaoException;
import dao.GameDao;

public final class DbFormComment {
      
    private CommentDao commentDao;

    public DbFormComment( CommentDao commentDao ) {
        this.commentDao = commentDao;
    }
        
    //nouvelle methode addGame, mais avec des parametres different
    public void addComment( int idGame, int idUser, String messageComment ) {
    	Comment comment = new Comment();
    	
    	//sauvegarde de l'id du jeu
        Game game = new Game();
        game.setIdGame(idGame);
        comment.setGame(game);
        
        //sauvegarde de l'id du user
        User user = new User();
        user.setIdUser(idUser);
        comment.setUser(user);       
    	
    	comment.setMessageComment(messageComment);
    	
    	try {          
            commentDao.create( comment );            
        
	    } catch ( DaoException e ) {	        
	        e.printStackTrace();
	    }
    	
        
    }
   
}