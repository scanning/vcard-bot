package ng.i.cann.s.vcard.state.mongo;

import java.util.Date;

import org.mongojack.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ezvcard.Ezvcard;
import ezvcard.VCard;

/**
 * A VCard directory entry.
 * 
 * @author scanning
 *
 */
public class VCardDirectoryEntry {

	private static final int VERSION = 1;

	@Id
	private String phoneNumber;

	@JsonIgnore
	private transient VCard vcard;

	@JsonProperty
	private String vcardString;

	@JsonProperty
	private Date created;

	@JsonProperty
	private Date modified;

	@JsonProperty
	private int version;

	private VCardDirectoryEntry() {
		super();
	}

	public VCardDirectoryEntry(String phoneNumber, VCard vcard) {
		this();
		this.version = VERSION;
		this.phoneNumber = phoneNumber;
		this.vcardString = vcard.write();
		this.created = new Date();
		this.modified = new Date();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@JsonIgnore
	public VCard getVcard() {
		if (vcard == null) {
			vcard = Ezvcard.parse(vcardString).first();
		}
		return vcard;
	}

	public Date getCreated() {
		return created;
	}

	public Date getModified() {
		return modified;
	}

	public int getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((modified == null) ? 0 : modified.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((vcardString == null) ? 0 : vcardString.hashCode());
		result = prime * result + version;
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
		VCardDirectoryEntry other = (VCardDirectoryEntry) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (modified == null) {
			if (other.modified != null)
				return false;
		} else if (!modified.equals(other.modified))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (vcardString == null) {
			if (other.vcardString != null)
				return false;
		} else if (!vcardString.equals(other.vcardString))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VCardDirectoryEntry [phoneNumber=" + phoneNumber + ", vcardString=" + vcardString + ", created=" + created + ", modified=" + modified
				+ ", version=" + version + "]";
	}

}
