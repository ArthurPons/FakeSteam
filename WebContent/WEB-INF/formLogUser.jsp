<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Log user</title>
       
    </head>
    <body>
       <form method="post" action="logUser">
         <fieldset>
                <legend>User</legend>
                <p>Connecter un user</p>

                username:
                <input  id="username" name="username" value="" size="20" maxlength="60" />
                 <br/>
                password :
                <input  id="pwd" name="pwd" value="" size="20" maxlength="60" />
                <br/>              
                               
               <input type="submit" value="addUser" class="sansLabel" />

               
            </fieldset>
        </form>
    </body>
</html>