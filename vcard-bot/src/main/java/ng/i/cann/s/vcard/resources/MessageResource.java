package ng.i.cann.s.vcard.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ng.i.cann.s.vcard.twilio.TwilioConstants;

import com.codahale.metrics.annotation.Timed;

/**
 * Resource where incoming messages are sent to.
 * 
 * @author scanning
 *
 */
@Path("/message")
@Produces(MediaType.APPLICATION_XML)
public class MessageResource {

	public MessageResource() {
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Timed
	public String receiveMessage(@FormParam(TwilioConstants.BODY_FORM_PARAM) String body) {
		return body;
	}

}
