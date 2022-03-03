/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawls;

import daos.NewsDAO;
import dtos.NewsDTO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author PC
 */
@Path("generic")
public class NewsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of NewsResource
     */
    public NewsResource() {
    }

    /**
     * Retrieves representation of an instance of crawls.NewsResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of NewsResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @GET
    @Path("/findByTitle")
    @Produces(MediaType.APPLICATION_XML)
    public List<NewsDTO> findByTitle(@QueryParam("title") String search) {
        List<NewsDTO> result = null;
        try {   
            NewsDAO dao = new NewsDAO();
            result = dao.findByLikeTitle(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
