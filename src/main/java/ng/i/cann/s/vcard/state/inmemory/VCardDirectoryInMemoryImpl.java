package ng.i.cann.s.vcard.state.inmemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ezvcard.VCard;
import ng.i.cann.s.vcard.state.VCardDirectory;

/**
 * In-memory implementation of the VCard Directory interface.
 * 
 * @author scanning
 *
 */
public class VCardDirectoryInMemoryImpl implements VCardDirectory {

	private Map<String, VCard> cards;

	public VCardDirectoryInMemoryImpl(Map<String, VCard> cards) {
		this.cards = cards;
	}

	@Override
	public void addCard(String phoneNumber, VCard vcard) {
		cards.put(phoneNumber, vcard);
	}

	@Override
	public boolean exists(String phoneNumber) {
		return cards.containsKey(phoneNumber);
	}

	@Override
	public VCard lookup(String phoneNumber) {
		return cards.get(phoneNumber);
	}

	@Override
	public List<String> allNumbers() {
		return new ArrayList<>(Collections.unmodifiableSet(cards.keySet()));
	}

}
