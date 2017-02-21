package org.mytest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by marijn on 16-2-17.
 */
@Path("/hello")
public class HelloWorldService {

    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMessage(@PathParam("param") String message) {
        String output = "Jersey says: " + message;
        return Response.status(200).entity(output).build();
    }
}
