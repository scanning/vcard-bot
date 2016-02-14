package ng.i.cann.s.vcard.workers;

import java.util.List;

import ng.i.cann.s.vcard.ExternalUrlConfiguration;

import com.twilio.sdk.TwilioRestClient;

/**
 * Send all existing cards to the user.
 * 
 * @author scanning
 *
 */
public final class SendCard extends CardWorker {

	public SendCard(String from, TwilioRestClient twilioRestClient, ExternalUrlConfiguration externalUrlConfiguration, List<String> numbers,
			String current) {
		super(from, twilioRestClient, externalUrlConfiguration, numbers, current);
	}

	@Override
	protected void process(String number, String current) {
		try {
			sendVCardTo(current, number);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}
