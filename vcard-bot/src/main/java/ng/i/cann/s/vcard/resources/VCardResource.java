package ng.i.cann.s.vcard.resources;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

/**
 * Resource to obtain a v-card.
 * 
 * @author scanning
 *
 */
@Path("/vcard")
public class VCardResource {

	public VCardResource() {
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Timed
	public File getVCard(@PathParam("identifier") String identifier) {
		return null;
	}

}
