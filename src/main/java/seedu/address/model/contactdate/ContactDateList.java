package seedu.address.model.contactdate;

import java.util.ArrayList;

/**
 * Represents a List of ContactDates in the address book.
 */
public class ContactDateList extends ArrayList<ContactDate> {

    public ContactDateList() {
        super();
    }

    /**
     * Creates a ContactDateList with the given ContactDates.
     * @param contactDatesToAdd
     */
    public ContactDateList(ContactDate ...contactDatesToAdd) {
        super();
        for (ContactDate contactDate : contactDatesToAdd) {
            this.add(contactDate);
        }
    }

    public ContactDateList(ArrayList<ContactDate> contactDates) {
        super(contactDates);
    }

    /**
     * Adds all the ContactDates in the given ContactDateList to this ContactDateList.
     * @param contactDateList
     */
    public void addAll(ContactDateList contactDateList) {
        for (ContactDate contactDate : contactDateList) {
            this.add(contactDate);
        }
    }

    /**
     * Adds the current date to the ContactDateList.
     */
    public void markAsContacted() {
        this.add(ContactDate.getCurrentDate());
    }

    /**
     * Returns the last ContactDate in the ContactDateList.
     * @return the most recent ContactDate.
     */
    public ContactDate getLastContacted() {
        return this.get(this.size() - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return false;
    }
}
