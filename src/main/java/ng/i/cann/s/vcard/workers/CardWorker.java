package ng.i.cann.s.vcard.workers;

import java.util.ArrayList;
import java.util.List;

import ng.i.cann.s.vcard.ExternalUrlConfiguration;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;

/**
 * Base class for card workers.
 * 
 * @author scanning
 *
 */
public abstract class CardWorker implements Runnable {

	private final String sender;
	private final TwilioRestClient twilioRestClient;
	private final List<String> numbers;
	private final String current;
	private final ExternalUrlConfiguration externalUrlConfiguration;

	public CardWorker(String sender, TwilioRestClient twilioRestClient, ExternalUrlConfiguration externalUrlConfiguration, List<String> numbers,
			String current) {
		this.sender = sender;
		this.twilioRestClient = twilioRestClient;
		this.externalUrlConfiguration = externalUrlConfiguration;
		this.numbers = numbers;
		this.current = current;
	}

	@Override
	public void run() {
		for (String number : numbers) {
			if (number.equals(current)) {
				continue;
			}
			process(number, current);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Process the combination of a number and the current number.
	 * 
	 * @param number
	 *            the number being processed from the group of v-card numbers
	 * @param current
	 *            the current number of the person who sent in a v-card
	 */
	protected abstract void process(String number, String current);

	/**
	 * Send a vcard to someone.
	 * 
	 * @param to
	 * @param vcard
	 */
	protected void sendVCardTo(String to, String vcard) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("From", sender));
		params.add(new BasicNameValuePair("To", "+" + to));
		params.add(new BasicNameValuePair("Body", ""));

		String mediaUrl = buildMediaUrl(vcard);
		params.add(new BasicNameValuePair("MediaUrl", mediaUrl));

		try {
			twilioRestClient.getAccount().getMessageFactory().create(params);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}

	private String buildMediaUrl(String vcard) {
		StringBuilder sb = new StringBuilder();
		String scheme = externalUrlConfiguration.getScheme();
		int port = externalUrlConfiguration.getPort();
		sb.append(scheme);
		sb.append("://");
		sb.append(externalUrlConfiguration.getHost());
		if (scheme.equals("https") && port != 443 || scheme.equals("http") && port != 80) {
			sb.append(":");
			sb.append(externalUrlConfiguration.getPort());
		}
		sb.append("/vcard/");
		sb.append(vcard);
		return sb.toString();
	}
}
