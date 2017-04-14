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
import dao.UserDao;

public final class CheckFormComment {
    private static final String CHAMP_GAME = "game";
    private static final String CHAMP_USER = "user";
    
   
    private String resultat;
    private Map<String, String> erreurs          = new HashMap<String, String>();
    private CommentDao commentDao;

    public CheckFormComment( CommentDao commentDao ) {
        this.commentDao = commentDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Comment addComment( HttpServletRequest request ) {
        String game = getValeurChamp( request, CHAMP_GAME );
        String user = getValeurChamp( request, CHAMP_USER );
        
        
        System.out.print("game :"+game+"\n");
        System.out.print("user"+user+"\n");
        Comment comment = new Comment();
        
        return null;
        /*
        comment
        user.setPwdUser(pwd);
           
        
        try {          
                userDao.create( user );
                resultat = "Succès de l'inscription.";
            
        } catch ( DaoException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return user;
        */
    }



    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}