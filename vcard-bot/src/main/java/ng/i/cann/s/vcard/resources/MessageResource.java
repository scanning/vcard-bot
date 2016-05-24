package ng.i.cann.s.vcard.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ng.i.cann.s.vcard.ExternalUrlConfiguration;
import ng.i.cann.s.vcard.state.VCardDirectory;
import ng.i.cann.s.vcard.twilio.TwilioConstants;
import ng.i.cann.s.vcard.workers.SendCard;
import ng.i.cann.s.vcard.workers.UpdateCards;

/**
 * Resource where incoming messages are sent to.
 * 
 * @author scanning
 *
 */
@Path("/message")
@Produces(MediaType.APPLICATION_XML)
public class MessageResource {

	private static final String VCARD_MEDIA_TYPE = "text/vcard";

	private final VCardDirectory directory;
	private final ExecutorService updateCardsExecutor;
	private final ExecutorService sendCardExecutor;
	private final Client client;
	private final String sender;
	private final TwilioRestClient twilioRestClient;
	private final ExternalUrlConfiguration externalUrlConfiguration;

	public MessageResource(VCardDirectory directory, ExecutorService updateCardsExecutor, ExecutorService sendCardExecutor, Client client,
			String sender, TwilioRestClient twilioRestClient, ExternalUrlConfiguration externalUrlConfiguration) {
		this.directory = directory;
		this.updateCardsExecutor = updateCardsExecutor;
		this.sendCardExecutor = sendCardExecutor;
		this.client = client;
		this.sender = sender;
		this.twilioRestClient = twilioRestClient;
		this.externalUrlConfiguration = externalUrlConfiguration;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Timed
	public String receiveMessage(@FormParam(TwilioConstants.FROM_FORM_PARAM) String from, @FormParam(TwilioConstants.TO_FORM_PARAM) String to,
			@FormParam(TwilioConstants.NUM_MEDIA_FORM_PARAM) String numMedia, @FormParam(TwilioConstants.BODY_FORM_PARAM) String body,
			@FormParam(TwilioConstants.MEDIA_URL_FORM_PARAM + "0") String mediaUrl0,
			@FormParam(TwilioConstants.MEDIA_CONTENT_TYPE_FORM_PARAM + "0") String mediaContentType0) {

		TwiMLResponse twiml = new TwiMLResponse();
		Message message = new Message("Thanks for registering with the directory.");

		if (notNullOrEmpty(from) && notNullOrEmpty(numMedia) && notNullOrEmpty(mediaUrl0) && notNullOrEmpty(mediaContentType0)) {
			from = from.substring(1);
			boolean newCard = (!directory.exists(from));
			int media = Integer.parseInt(numMedia);
			if (media == 1 && VCARD_MEDIA_TYPE.equals(mediaContentType0)) {
				Response r = client.target(mediaUrl0).request(mediaContentType0).buildGet().invoke();
				if (r.getStatus() == 301) {
					// The client doesn't follow the 301 so lets do it.
					URI u = r.getLocation();
					r = client.target(u).request(mediaContentType0).buildGet().invoke();
				}

				if (r.getStatus() == 200) {
					String vcardString = r.readEntity(String.class);
					VCard vcard = Ezvcard.parse(vcardString).first();
					directory.addCard(from, vcard);
					List<String> numbers = new ArrayList<>(directory.allNumbers());
					if (newCard) {
						SendCard send = new SendCard(sender, twilioRestClient, externalUrlConfiguration, numbers, from);
						sendCardExecutor.execute(send);
					}
					UpdateCards update = new UpdateCards(sender, twilioRestClient, externalUrlConfiguration, numbers, from);
					updateCardsExecutor.execute(update);
					try {
						twiml.append(message);
					} catch (TwiMLException e) {
					}
				}
			}
		}

		return twiml.toXML();
	}

	private static boolean notNullOrEmpty(String s) {
		return (s != null && s.length() > 0);
	}

}
