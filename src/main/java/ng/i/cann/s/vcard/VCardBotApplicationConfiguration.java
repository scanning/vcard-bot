package ng.i.cann.s.vcard;

import io.dropwizard.Configuration;
import ng.i.cann.s.vcard.slack.SlackConfiguration;
import ng.i.cann.s.vcard.state.mongo.MongoConfiguration;
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

	private ExternalUrlConfiguration externalUrl = new ExternalUrlConfiguration();

	public ExternalUrlConfiguration getExternalUrl() {
		return externalUrl;
	}

	private SlackConfiguration slack = new SlackConfiguration();

	public SlackConfiguration getSlack() {
		return slack;
	}

	private MongoConfiguration mongo = new MongoConfiguration();

	public MongoConfiguration getMongo() {
		return mongo;
	}

}
