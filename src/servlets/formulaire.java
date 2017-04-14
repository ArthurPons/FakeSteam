package servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import bean.Game;
import dao.DaoException;
import dao.GameDao;

public final class formulaire {
    private static final String CHAMP_URL = "url";
    private static final String CHAMP_PRICE = "price";
    private static final String CHAMP_TITLE  = "title";
    
   
    private String resultat;
    private Map<String, String> erreurs          = new HashMap<String, String>();
    private GameDao      gameDao;

    public formulaire( GameDao gameDao ) {
        this.gameDao = gameDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Game addGame( HttpServletRequest request ) {
        String url = getValeurChamp( request, CHAMP_URL );
        String price = getValeurChamp( request, CHAMP_PRICE );
        String title = getValeurChamp( request, CHAMP_TITLE );
        
        Game game = new Game();
        game.setPictureUrlGame(url);
        game.setPriceGame((float)Integer.parseInt(price));
        game.setTitleGame(title);
        
        try {          
                gameDao.create( game );
                resultat = "Succès de l'inscription.";
            
        } catch ( DaoException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return game;
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