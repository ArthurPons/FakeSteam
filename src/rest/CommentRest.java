package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.SerializationFeature;

import bean.Comment;
import bean.Game;
import bean.User;
import dao.DaoFactory;

@Path("Comment")
public class CommentRest {
		
	@GET
	@Path("/get/{i}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Comment printCommentById(@PathParam("i") int i) {
		
		System.out.print("valeur :"+i+"\n");
		DaoFactory fact = DaoFactory.getInstance();
        dao.CommentDao commentDao = fact.getCommentDao();
        
		
        Comment c = commentDao.find(i);       
     
		return c;
		
	}
	
	

}