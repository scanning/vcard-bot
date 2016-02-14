package ng.i.cann.s.vcard.config;

import io.dropwizard.Configuration;
import ng.i.cann.s.vcard.twilio.TwilioConfiguration;

/**
 * Configuration for the V-Card bot application.
 * 
 * @author scanning
 *
 */
public class VCardBotApplicationConfiguration extends Configuration {

	private TwilioConfiguration twilio = new TwilioConfiguration();

	public TwilioConfiguration getTwilio() {
		return twilio;
	}

}
