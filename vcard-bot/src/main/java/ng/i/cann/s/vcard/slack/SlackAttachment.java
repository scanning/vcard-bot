package ng.i.cann.s.vcard.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Slack attachment, forms part of a SlackResponse.
 * 
 * @author scanning
 *
 */
public class SlackAttachment {

	@JsonProperty
	private String fallback;

	@JsonProperty
	private String color;

	@JsonProperty
	private String pretext;

	@JsonProperty("author_name")
	private String authorName;

	@JsonProperty("author_link")
	private String authorLink;

	@JsonProperty("author_icon")
	private String authorIcon;

	@JsonProperty
	private String title;

	@JsonProperty("title_link")
	private String titleLink;

	@JsonProperty
	private String text;

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("thumb_url")
	private String thumbUrl;

	private SlackAttachment() {
		super();
	}

	public SlackAttachment(String fallback, String color, String pretext, String authorName, String authorLink, String authorIcon, String title,
			String titleLink, String text, String imageUrl, String thumbUrl) {
		this();
		this.fallback = fallback;
		this.color = color;
		this.pretext = pretext;
		this.authorName = authorName;
		this.authorLink = authorLink;
		this.authorIcon = authorIcon;
		this.title = title;
		this.titleLink = titleLink;
		this.text = text;
		this.imageUrl = imageUrl;
		this.thumbUrl = thumbUrl;
	}

	public String getFallback() {
		return fallback;
	}

	public String getColor() {
		return color;
	}

	public String getPretext() {
		return pretext;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getAuthorLink() {
		return authorLink;
	}

	public String getAuthorIcon() {
		return authorIcon;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleLink() {
		return titleLink;
	}

	public String getText() {
		return text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPretext(String pretext) {
		this.pretext = pretext;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	public void setAuthorIcon(String authorIcon) {
		this.authorIcon = authorIcon;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorIcon == null) ? 0 : authorIcon.hashCode());
		result = prime * result + ((authorLink == null) ? 0 : authorLink.hashCode());
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((fallback == null) ? 0 : fallback.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((pretext == null) ? 0 : pretext.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((thumbUrl == null) ? 0 : thumbUrl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((titleLink == null) ? 0 : titleLink.hashCode());
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
		SlackAttachment other = (SlackAttachment) obj;
		if (authorIcon == null) {
			if (other.authorIcon != null)
				return false;
		} else if (!authorIcon.equals(other.authorIcon))
			return false;
		if (authorLink == null) {
			if (other.authorLink != null)
				return false;
		} else if (!authorLink.equals(other.authorLink))
			return false;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (fallback == null) {
			if (other.fallback != null)
				return false;
		} else if (!fallback.equals(other.fallback))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (pretext == null) {
			if (other.pretext != null)
				return false;
		} else if (!pretext.equals(other.pretext))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (thumbUrl == null) {
			if (other.thumbUrl != null)
				return false;
		} else if (!thumbUrl.equals(other.thumbUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (titleLink == null) {
			if (other.titleLink != null)
				return false;
		} else if (!titleLink.equals(other.titleLink))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SlackAttachment [fallback=" + fallback + ", color=" + color + ", pretext=" + pretext + ", authorName=" + authorName + ", authorLink="
				+ authorLink + ", authorIcon=" + authorIcon + ", title=" + title + ", titleLink=" + titleLink + ", text=" + text + ", imageUrl="
				+ imageUrl + ", thumbUrl=" + thumbUrl + "]";
	}

}
