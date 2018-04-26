/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        if (acceptedUsername.equals(username) && acceptedPassword.equals(password)){
            return Response.ok().entity("{\"UID\" : \"10000\"}").build();
        }
        return Response.ok().entity("{\"UID\" : \"99999\"}").build();
    }

}
