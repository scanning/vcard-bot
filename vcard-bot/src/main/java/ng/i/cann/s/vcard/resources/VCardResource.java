package ng.i.cann.s.vcard.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.codahale.metrics.annotation.Timed;

import ezvcard.VCard;

/**
 * Resource to obtain a v-card.
 * 
 * @author scanning
 *
 */
@Path("/vcard")
public class VCardResource {

	private final Map<String, VCard> vcards;

	public VCardResource(Map<String, VCard> vcards) {
		this.vcards = vcards;
	}

	@GET
	@Path("{number}")
	@Produces("text/vcard")
	@Timed
	public String getVCard(@PathParam("number") String number) {
		String result = null;
		if (number != null && number.length() > 0 && vcards.containsKey(number)) {
			VCard vcard = vcards.get(number);
			if (vcard != null) {
				result = vcard.write();
			}
		}
		return result;
	}

}
