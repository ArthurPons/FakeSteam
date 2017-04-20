<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
       
    </head>
    <body>
        <form method="post" action="inscription">
	    <fieldset>
                <legend>Game</legend>
                <p>Ajouter un jeu</p>

                url:
                <input  id="url" name="url" value="" size="20" maxlength="60" />
                 <br/>
                price :
                <input  id="price" name="price" value="" size="20" maxlength="60" />
                <br/>
                
                title :                
                <input  id="url" name="title" value="" size="20" maxlength="60" />
                
               <input type="submit" value="addGame" class="sansLabel" />
                              
            </fieldset>            
         </form>
                
    </body>
</html>