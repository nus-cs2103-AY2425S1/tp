package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.ReminderAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;

/**
 * A utility class to help with building ReminderAddressbook objects.
 * Example usage: <br>
 *     {@code ReminderAddressBook rab = new ReminderAddressBookBuilder().withReminder(MEETING, "Doe").build();}
 */
public class ReminderAddressBookBuilder {

    private ReminderAddressBook reminderAddressBook;

    public ReminderAddressBookBuilder() {
        reminderAddressBook = new ReminderAddressBook();
    }

    public ReminderAddressBookBuilder(ReminderAddressBook reminderAddressBook) {
        this.reminderAddressBook = reminderAddressBook;
    }

    /**
     * Adds a new {@code reminder} to the {@code ReminderAddressBook} that we are building.
     */
    public ReminderAddressBookBuilder withReminder(Reminder reminder) {
        reminderAddressBook.addReminder(reminder);
        return this;
    }

    public ReminderAddressBook build() {
        return reminderAddressBook;
    }
}
