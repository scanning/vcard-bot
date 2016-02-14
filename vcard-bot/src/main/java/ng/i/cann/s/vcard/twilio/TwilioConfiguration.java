package ng.i.cann.s.vcard.twilio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwilioConfiguration {

	@JsonProperty
	private String accountSid;

	@JsonProperty
	private String authToken;

	public String getAccountSid() {
		return accountSid;
	}

	public String getAuthToken() {
		return authToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountSid == null) ? 0 : accountSid.hashCode());
		result = prime * result + ((authToken == null) ? 0 : authToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwilioConfiguration other = (TwilioConfiguration) obj;
		if (accountSid == null) {
			if (other.accountSid != null)
				return false;
		} else if (!accountSid.equals(other.accountSid))
			return false;
		if (authToken == null) {
			if (other.authToken != null)
				return false;
		} else if (!authToken.equals(other.authToken))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TwilioConstants [accountSid=" + accountSid + ", authToken=" + authToken + "]";
	}

}
