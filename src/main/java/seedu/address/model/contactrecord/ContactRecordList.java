package seedu.address.model.contactrecord;

import java.util.ArrayList;

/**
 * Represents a List of ContactRecords in the address book.
 */
public class ContactRecordList extends ArrayList<ContactRecord> {

    public ContactRecordList() {
        super();
    }

    /**
     * Creates a ContactRecordList with the given ContactRecords.
     * @param contactRecordsToAdd
     */
    public ContactRecordList(ContactRecord...contactRecordsToAdd) {
        super();
        for (ContactRecord contactRecord : contactRecordsToAdd) {
            this.add(contactRecord);
        }
    }

    public ContactRecordList(ArrayList<ContactRecord> contactRecords) {
        super(contactRecords);
    }

    /**
     * Adds all the ContactRecords in the given ContactRecordList to this ContactRecordList.
     * @param contactRecordList
     */
    public void addAll(ContactRecordList contactRecordList) {
        for (ContactRecord contactRecord : contactRecordList) {
            this.add(contactRecord);
        }
    }

    /**
     * Adds the current record to the ContactRecordList.
     */
    public void markAsContacted(String notes) {
        this.add(ContactRecord.createCurrentRecord(notes));
    }

    /**
     * Adds the given ContactRecord to the ContactRecordList.
     * @param contactRecord
     */
    public void markAsContacted(ContactRecord contactRecord) {
        this.add(contactRecord);
    }

    /**
     * Returns the last ContactRecord in the ContactRecordList.
     * @return the most recent ContactRecord.
     * @throws IndexOutOfBoundsException if the list is empty.
     */
    public ContactRecord getLastContacted() {
        if (this.size() == 0) {
            throw new IndexOutOfBoundsException("No ContactRecords in the list.");
        }
        return this.get(this.size() - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactRecordList)) {
            return false;
        }

        ContactRecordList that = (ContactRecordList) other;

        if (this.size() == that.size()) {
            for (int i = 0; i < this.size(); i++) {
                if (!this.get(i).equals(that.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
