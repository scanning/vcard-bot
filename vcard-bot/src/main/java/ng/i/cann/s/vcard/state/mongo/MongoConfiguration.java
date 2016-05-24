package ng.i.cann.s.vcard.state.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.util.UriEncoder;

public class MongoConfiguration {

	@JsonProperty
	private String user;

	public String getUser() {
		if (user == null) {
			user = System.getenv("MONGO_DB_USER");
		}
		return user;
	}

	@JsonProperty
	private String password;

	public String getPassword() {
		if (password == null) {
			password = System.getenv("MONGO_DB_PASSWORD");
		}
		return password;
	}

	@JsonProperty
	private String host;

	public String getHost() {
		if (host == null) {
			host = System.getenv("MONGO_DB_HOST");
		}
		return host;
	}

	@JsonProperty
	private int port = -1;

	public int getPort() {
		if (port == -1) {
			String portStr = System.getenv("MONGO_DB_PORT");
			if (portStr != null && portStr.length() > 0) {
				port = Integer.parseInt(portStr);
			} else {
				port = 27017;
			}
		}
		return port;
	}

	@JsonProperty
	private String name;

	public String getName() {
		if (name == null) {
			name = System.getenv("MONGO_DB_NAME");
		}
		return name;
	}

	@JsonIgnore
	public String getURI() {

		StringBuilder sb = new StringBuilder();
		sb.append("mongodb://");
		if (getUser() != null && getUser().length() > 0 && getPassword() != null && getPassword() != null) {
			sb.append(UriEncoder.encode(getUser()));
			sb.append(":");
			sb.append(UriEncoder.encode(getPassword()));
			sb.append("@");
		}
		sb.append(getHost());
		sb.append(":");
		sb.append(getPort());
		sb.append("/");
		sb.append(getName());
		return sb.toString();
	}
}
