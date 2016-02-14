package ng.i.cann.s.vcard;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the external facing URL configuration.
 * 
 * @author scanning
 *
 */
public class ExternalUrlConfiguration {

	@JsonProperty
	private String scheme;

	@JsonProperty
	private String host;

	@JsonProperty
	private int port;

	public String getScheme() {
		return scheme;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

}
