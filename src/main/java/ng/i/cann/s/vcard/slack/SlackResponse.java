package ng.i.cann.s.vcard.slack;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackResponse {

	@JsonProperty
	private String text;

	@JsonProperty
	private List<SlackAttachment> attachments;

	private SlackResponse() {
		super();
	}

	public SlackResponse(String text) {
		this(text, Collections.emptyList());
	}

	public SlackResponse(String text, List<SlackAttachment> attachments) {
		this();
		this.text = text;
		this.attachments = attachments;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<SlackAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SlackAttachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		SlackResponse other = (SlackResponse) obj;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SlackResponse [text=" + text + ", attachments=" + attachments + "]";
	}

}
