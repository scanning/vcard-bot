package ng.i.cann.s.vcard.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.map.ObjectMapper;

import ezvcard.VCard;
import ezvcard.property.Email;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;
import ng.i.cann.s.vcard.slack.SlackAttachment;
import ng.i.cann.s.vcard.slack.SlackResponse;
import ng.i.cann.s.vcard.state.VCardDirectory;

/**
 * Worker that searches through cards.
 * 
 * @author scanning
 *
 */
public class SearchCards implements Runnable {

	private final String search;
	private final String responseUrl;
	private final VCardDirectory directory;
	private final HttpClient httpClient;

	public SearchCards(String search, String responseUrl, VCardDirectory directory, HttpClient httpClient) {
		this.search = search;
		this.responseUrl = responseUrl;
		this.directory = directory;
		this.httpClient = httpClient;
	}

	@Override
	public void run() {
		List<VCard> results = null;
		for (String number : directory.allNumbers()) {
			VCard vcard = directory.lookup(number);
			if (vcard != null) {
				List<FormattedName> names = vcard.getFormattedNames();
				for (FormattedName formattedName : names) {
					String name = formattedName.getValue();
					if (name != null) {
						String lcName = name.toLowerCase();
						if (lcName.contains(search)) {
							if (results == null) {
								results = new ArrayList<>();
							}
							results.add(vcard);
						}
					}
				}
			}
		}

		SlackResponse slackResponse = null;
		if (results != null && results.size() > 0) {
			List<SlackAttachment> attachments = new ArrayList<SlackAttachment>();
			for (VCard card : results) {
				FormattedName name = card.getFormattedName();
				StringBuffer sb = new StringBuffer();
				List<Telephone> telephones = card.getTelephoneNumbers();
				for (Telephone telephone : telephones) {
					String value = telephone.getText();
					sb.append(value);
					sb.append("\n");
				}
				List<Email> emails = card.getEmails();
				for (Email email : emails) {
					String value = email.getValue();
					sb.append(value);
					sb.append("\n");
				}

				attachments.add(new SlackAttachment("", "#36a64f", name.getValue(), "", "", "", "", "", sb.toString(), "", ""));
			}
			slackResponse = new SlackResponse("The results are in for your '" + search + "' search.", attachments);
		} else {
			slackResponse = new SlackResponse("Ooh, that's embarrassing. I searched for '" + search + "' but couldn't find anything.");
		}

		try {
			HttpPost request = new HttpPost(responseUrl);
			request.setEntity(new ByteArrayEntity(new ObjectMapper().writeValueAsString(slackResponse).getBytes("UTF8")));
			request.setHeader("Content-Type", "application/json");
			httpClient.execute(request);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

	}
}
