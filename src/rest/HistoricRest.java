package rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoFactory;
import bean.Historic;



@Path("historic")
public class HistoricRest {

	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJson(Historic historic) throws URISyntaxException
	{
		System.out.print("passe Historic REST ************\n");
		System.out.println(historic);
		
		DaoFactory fact = DaoFactory.getInstance();
        dao.HistoricDao historicDao = fact.getHistoricDao();
		
        try {
        	historicDao.create(historic);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		String output = "Objet "+historic+" créé";
		return Response.status(200).entity(output).build();
	}
	
}
