package org.vitality.auth.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1-SNAPSHOT
 */
@Path("/authservice")
public class VitalityAuthService {

    //Sample Request
    //http://localhost:8080/authservice/getuid?username=admin&password=admin
    @GET
    @Path("/getuid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("username") String username, @QueryParam("password") String password) {

        final String acceptedUsername = "admin";
        final String acceptedPassword = "admin";
        if (acceptedUsername.equals(username) && acceptedPassword.equals(password)) {
            return Response.ok().entity("{\"UID\" : \"10000\"}").build();
        }
        return Response.ok().entity("{\"UID\" : \"99999\"}").build();
    }

}
