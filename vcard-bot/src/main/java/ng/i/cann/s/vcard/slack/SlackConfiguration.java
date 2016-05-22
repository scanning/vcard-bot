package ng.i.cann.s.vcard.slack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Slack configuration object.
 * 
 * @author scanning
 *
 */
public class SlackConfiguration {

	@JsonProperty
	private String token;

	public String getToken() {
		return token;
	}
	
	@JsonProperty 
	private String teamId;
	
	public String getTeamId() {
		return teamId;
	}
	
	@JsonProperty
	private String teamDomain;
	
	public String getTeamDomain() {
		return teamDomain;
	}
	

	@JsonProperty
	private List<String> commands;

	public List<String> getCommands() {
		return commands;
	}
}
