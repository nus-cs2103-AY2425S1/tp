package seedu.address.model.contactdate;

import java.util.ArrayList;

/**
 * Represents a List of ContactDates in the address book.
 */
public class ContactDateList extends ArrayList<ContactDate> {

    public ContactDateList() {
        super();
    }

    public ContactDateList(ContactDate ...contactDatesToAdd) {
        super();
        for (ContactDate contactDate : contactDatesToAdd) {
            this.add(contactDate);
        }
    }

    public ContactDateList(ArrayList<ContactDate> contactDates) {
        super(contactDates);
    }

    public void addAll(ContactDateList contactDateList) {
        for (ContactDate contactDate : contactDateList) {
            this.add(contactDate);
        }
    }

    public void markAsContacted() {
        this.add(ContactDate.getCurrentDate());
    }

    public ContactDate getLastContacted() {
        return this.get(this.size() - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactDateList)) {
            return false;
        }

        ContactDateList otherList = (ContactDateList) other;
        return this.equals(otherList);
    }
}
