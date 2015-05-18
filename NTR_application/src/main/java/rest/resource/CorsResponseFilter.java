package main.java.rest.resource;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Allow the system to serve xhr level 2 from all cross domain site
 */
@Provider
public class CorsResponseFilter implements ContainerResponseFilter {
	/**
	 * Add the cross domain data to the output if needed
	 *
	 * @param creq The container request (input)
	 * @param cres The container request (output)
	 * @return The output request with cross domain if needed
	 */
	@Override
	public void filter(ContainerRequestContext creq, ContainerResponseContext cres) throws IOException {
		cres.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        cres.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, auth-token");
        cres.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().putSingle("Access-Control-Max-Age", "1209600");
	}
}