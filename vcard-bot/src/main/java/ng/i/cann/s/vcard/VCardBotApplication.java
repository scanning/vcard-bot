package ng.i.cann.s.vcard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.Charset;

import ng.i.cann.s.vcard.config.VCardBotApplicationConfiguration;
import ng.i.cann.s.vcard.resources.MessageResource;
import ng.i.cann.s.vcard.resources.VCardResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

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

		log.info("Setting up SMS endpoint resource");
		MessageResource messageResource = new MessageResource();
		environment.jersey().register(messageResource);

		log.info("Setting up v-card resource");
		VCardResource vcardResource = new VCardResource();
		environment.jersey().register(vcardResource);

		log.info("Initialization complete");
	}

}
