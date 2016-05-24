package ng.i.cann.s.vcard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.codahale.metrics.annotation.Timed;

import ezvcard.VCard;
import ng.i.cann.s.vcard.state.VCardDirectory;

/**
 * Resource to obtain a v-card.
 * 
 * @author scanning
 *
 */
@Path("/vcard")
public class VCardResource {

	private final VCardDirectory directory;

	public VCardResource(VCardDirectory directory) {
		this.directory = directory;
	}

	@GET
	@Path("{number}")
	@Produces("text/vcard")
	@Timed
	public String getVCard(@PathParam("number") String number) {
		String result = null;
		if (number != null && number.length() > 0 && directory.exists(number)) {
			VCard vcard = directory.lookup(number);
			if (vcard != null) {
				result = vcard.write();
			}
		}
		return result;
	}

}
