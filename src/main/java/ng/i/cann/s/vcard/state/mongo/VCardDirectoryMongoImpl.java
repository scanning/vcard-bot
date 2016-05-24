package ng.i.cann.s.vcard.state.mongo;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.mongodb.DB;

import ezvcard.VCard;
import ng.i.cann.s.vcard.state.VCardDirectory;

public class VCardDirectoryMongoImpl implements VCardDirectory {

	private final JacksonDBCollection<VCardDirectoryEntry, String> collection;

	public VCardDirectoryMongoImpl(DB db, String collectionName) {
		collection = JacksonDBCollection.wrap(db.getCollection(collectionName), VCardDirectoryEntry.class, String.class);
	}

	@Override
	public void addCard(String phoneNumber, VCard vcard) {
		VCardDirectoryEntry entry = new VCardDirectoryEntry(phoneNumber, vcard);
		collection.insert(entry);
	}

	@Override
	public boolean exists(String phoneNumber) {
		return (collection.findOneById(phoneNumber) != null);
	}

	@Override
	public VCard lookup(String phoneNumber) {
		VCard result = null;
		VCardDirectoryEntry entry = collection.findOneById(phoneNumber);
		if (entry != null) {
			result = entry.getVcard();
		}
		return result;
	}

	@Override
	public List<String> allNumbers() {
		List<String> result = new ArrayList<String>();
		DBCursor<VCardDirectoryEntry> cursor = collection.find();
		while (cursor.hasNext()) {
			VCardDirectoryEntry next = cursor.next();
			result.add(next.getPhoneNumber());
		}
		return result;
	}

}
