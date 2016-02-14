package ng.i.cann.s.vcard.workers;

import java.util.List;

import ng.i.cann.s.vcard.ExternalUrlConfiguration;

import com.twilio.sdk.TwilioRestClient;

/**
 * Send the updated card to all users.
 * 
 * @author scanning
 *
 */
public class UpdateCards extends CardWorker {

	public UpdateCards(String from, TwilioRestClient twilioRestClient, ExternalUrlConfiguration externalUrlConfiguration, List<String> numbers,
			String current) {
		super(from, twilioRestClient, externalUrlConfiguration, numbers, current);
	}

	@Override
	protected void process(String number, String current) {
		try {
			sendVCardTo(number, current);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}
