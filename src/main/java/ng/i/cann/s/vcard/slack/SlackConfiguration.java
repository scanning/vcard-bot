package ng.i.cann.s.vcard.slack;

import java.util.Arrays;
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
		if (token == null) {
			token = System.getenv("SLACK_TOKEN");
		}
		return token;
	}

	@JsonProperty
	private String teamId;

	public String getTeamId() {
		if (teamId == null) {
			teamId = System.getenv("SLACK_TEAM_ID");
		}
		return teamId;
	}

	@JsonProperty
	private String teamDomain;

	public String getTeamDomain() {
		if (teamDomain == null) {
			teamDomain = System.getenv("SLACK_TEAM_DOMAIN");
		}
		return teamDomain;
	}

	@JsonProperty
	private List<String> commands;

	public List<String> getCommands() {
		if (commands == null) {
			commands = Arrays.asList(System.getenv("SLACK_COMMANDS").split(","));
		}
		return commands;
	}
}
