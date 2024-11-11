package seedu.address.model.contact;

import java.util.Comparator;

/**
 * A comparator class that sorts the contacts by full name in terms of alphabetical order. If the full
 * name of the contacts is the same, the nickname will be used as the basis for sorting in
 * alphabetical order as well.
 */
public class ComparatorName implements Comparator<Contact> {
    private static final int EQUAL_STATUS = 0;

    /**
     * Compares the contact by name and then nickname in alphabetical order.
     * @param contact1 the first contact object to be compared.
     * @param contact2 the second contact object to be compared.
     * @return
     */
    public int compare(Contact contact1, Contact contact2) {
        int compareNumberByName = contact1.getName().compareTo(contact2.getName());
        if (compareNumberByName == EQUAL_STATUS) {
            return contact1.getNickname().compareTo(contact2.getNickname());
        }
        return compareNumberByName;
    }
}
