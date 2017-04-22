package servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import bean.Game;
import dao.DaoException;
import dao.GameDao;

public final class DbFormGame {
    private static final String CHAMP_URL = "url";
    private static final String CHAMP_PRICE = "price";
    private static final String CHAMP_TITLE  = "title";
  
    private GameDao      gameDao;

    public DbFormGame( GameDao gameDao ) {
        this.gameDao = gameDao;
    }

  
    public void addGame( HttpServletRequest request ) {
        String url = getValeurChamp( request, CHAMP_URL );
        String price = getValeurChamp( request, CHAMP_PRICE );
        String title = getValeurChamp( request, CHAMP_TITLE );
        
        Game game = new Game();
        game.setPictureUrlGame(url);
        game.setPriceGame((float)Integer.parseInt(price));
        game.setTitleGame(title);
        
        try {          
                gameDao.create( game );                
            
        } catch ( DaoException e ) {
            e.printStackTrace();
        }

      
    }



    /*
     * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
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