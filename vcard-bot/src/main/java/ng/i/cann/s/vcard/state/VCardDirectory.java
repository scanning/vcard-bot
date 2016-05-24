package ng.i.cann.s.vcard.state;

import java.util.List;

import ezvcard.VCard;

/**
 * An interface that defines the vcard directory.
 * 
 * @author scanning
 *
 */
public interface VCardDirectory {

	/**
	 * Add a VCard to the directory.
	 * 
	 * @param phoneNumber
	 * @param vcard
	 */
	public void addCard(String phoneNumber, VCard vcard);

	/**
	 * Does a phone number exist in the collection?
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public boolean exists(String phoneNumber);

	/**
	 * Get a VCard from the directory.
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public VCard lookup(String phoneNumber);

	/**
	 * @return all of the numbers that registered a VCard.
	 */
	public List<String> allNumbers();

}
