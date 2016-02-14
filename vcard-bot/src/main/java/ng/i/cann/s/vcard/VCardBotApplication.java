package ng.i.cann.s.vcard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import ng.i.cann.s.vcard.resources.MessageResource;
import ng.i.cann.s.vcard.resources.VCardResource;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twilio.sdk.TwilioRestClient;

import ezvcard.VCard;

/**
 * The Slack V-Card Bot application.
 * 
 * @author scanning
 *
 */
public class VCardBotApplication extends Application<VCardBotApplicationConfiguration> {

	private final static Logger log = LoggerFactory.getLogger(VCardBotApplication.class);

	public static void main(String[] args) throws Exception {
		new VCardBotApplication().run(args);
	}

	@Override
	public String getName() {
		return "V-Card Bot Service";
	}

	@Override
	public void initialize(Bootstrap<VCardBotApplicationConfiguration> bootstrap) {

	}

	@Override
	public void run(VCardBotApplicationConfiguration configuration, Environment environment) throws Exception {

		log.info("Starting v-card bot application");
		log.info("Default charset: {}", Charset.defaultCharset().name());
		log.info("Configuration:");

		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer().withFeatures(SerializationFeature.INDENT_OUTPUT);
		String json = writer.writeValueAsString(configuration);

		LineNumberReader lnr = new LineNumberReader(new StringReader(json));

		String line;
		for (line = lnr.readLine(); line != null; line = lnr.readLine()) {
			log.info("    {}", line);
		}

		Map<String, VCard> vcards = new HashMap<>();
		ExecutorService updateCards = Executors.newCachedThreadPool();
		ExecutorService sendCards = Executors.newCachedThreadPool();
		ClientConfig cc = new ClientConfig().property(ClientProperties.FOLLOW_REDIRECTS, true);
		Client client = ClientBuilder.newClient(cc);
		String sender = configuration.getTwilio().getSender();
		TwilioRestClient twilioRestClient = new TwilioRestClient(configuration.getTwilio().getAccountSid(), configuration.getTwilio().getAuthToken());

		log.info("Setting up SMS endpoint resource");
		MessageResource messageResource = new MessageResource(vcards, updateCards, sendCards, client, sender, twilioRestClient,
				configuration.getExternalUrl());
		environment.jersey().register(messageResource);

		log.info("Setting up v-card resource");
		VCardResource vcardResource = new VCardResource(vcards);
		environment.jersey().register(vcardResource);

		log.info("Initialization complete");
	}

}
