package seedu.address.model.contactdate;

import java.util.ArrayList;

/**
 * Represents a List of ContactDates in the address book.
 */
public class ContactDateList extends ArrayList<ContactDate> {
    private final ArrayList<ContactDate> contactDates;

    public ContactDateList() {
        contactDates = new ArrayList<>();
    }

    public ContactDateList(ContactDate ...contactDates) {
        this.contactDates = new ArrayList<>();
        for (ContactDate contactDate : contactDates) {
            this.contactDates.add(contactDate);
        }
    }

    public void addAll(ContactDateList contactDateList) {
        contactDates.addAll(contactDateList.contactDates);
    }

    public void markAsContacted() {
        contactDates.add(ContactDate.getCurrentDate());
    }

    public ContactDate getLastContacted() {
        return contactDates.get(contactDates.size() - 1);
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
        return contactDates.equals(otherList.contactDates);
    }
}
