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
		if (scheme == null) {
			scheme = System.getenv("EXT_URL_CONFIG_SCHEME");
		}
		return scheme;
	}

	public String getHost() {
		if (host == null) {
			host = System.getenv("EXT_URL_CONFIG_HOST");
		}
		return host;
	}

	public int getPort() {
		if (port == -1) {
			port = Integer.parseInt(System.getenv("EXT_URL_CONFIG_PORT"));
		}
		return port;
	}

}
