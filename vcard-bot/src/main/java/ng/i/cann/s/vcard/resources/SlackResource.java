package ng.i.cann.s.vcard.resources;

import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;

import com.codahale.metrics.annotation.Timed;

import ng.i.cann.s.vcard.slack.SlackConstants;
import ng.i.cann.s.vcard.slack.SlackResponse;
import ng.i.cann.s.vcard.state.VCardDirectory;
import ng.i.cann.s.vcard.workers.SearchCards;

/**
 * Resource where messages from Slack come in on to search for contacts.
 * 
 * @author scanning
 *
 */
@Path("/slack")
public class SlackResource {

	private String token;
	private String teamId;
	private final ExecutorService searchCardsExecutor;
	private final VCardDirectory directory;
	private final HttpClient httpClient;

	@Context
	private HttpServletResponse response;

	public SlackResource(String token, String teamId, ExecutorService searchCardsExecutor, VCardDirectory directory, HttpClient httpClient) {
		this.token = token;
		this.teamId = teamId;
		this.searchCardsExecutor = searchCardsExecutor;
		this.directory = directory;
		this.httpClient = httpClient;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public SlackResponse slack(@FormParam(SlackConstants.TOKEN_FORM_PARAM) String token, @FormParam(SlackConstants.TEAM_ID_FORM_PARAM) String teamId,
			@FormParam(SlackConstants.TEAM_DOMAIN_FORM_PARAM) String teamDomain, @FormParam(SlackConstants.CHANNEL_ID_FORM_PARAM) String channelId,
			@FormParam(SlackConstants.CHANNEL_NAME_FORM_PARAM) String channelName, @FormParam(SlackConstants.USER_ID_FORM_PARAM) String userId,
			@FormParam(SlackConstants.USER_NAME_FORM_PARAM) String userName, @FormParam(SlackConstants.COMMAND_FORM_PARAM) String command,
			@FormParam(SlackConstants.TEXT_FORM_PARAM) String text, @FormParam(SlackConstants.RESPONSE_URL_FORM_PARAM) String responseUrl) {

		SlackResponse response;

		if (!this.token.equals(token)) {
			throw new ForbiddenException();
		}

		if (!this.teamId.equals(teamId)) {
			throw new ForbiddenException();
		}

		String[] args = text.split("\\s+");
		if (args != null && args.length > 0) {
			SearchCards search = new SearchCards(args[0], responseUrl, directory, httpClient);
			searchCardsExecutor.execute(search);
			response = new SlackResponse("Got it! I'll be with you shortly.");
		} else {
			response = new SlackResponse("Steady on, enter a search term!");
		}

		return response;
	}

}
