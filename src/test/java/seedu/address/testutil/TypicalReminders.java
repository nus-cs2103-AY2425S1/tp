package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.ReminderAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final Reminder MEETINGJASON = new Reminder("10-10-2022",
                                                             "project meeting",
                                                             new Name("Jason"));
    public static final Reminder DINNERKATE = new Reminder("11-11-2022",
                                                           "dinner",
                                                           new Name("Kate"));
    public static final Reminder BASKETBALLCARL = new Reminder("05-07-2023",
                                                               "basketball practice",
                                                               new Name("Carl"));
    public static final Reminder JAPANTOM = new Reminder("12-12-2024",
                                                         "Japan trip",
                                                         new Name("Tom"));
    public static final Reminder LUNCHFIONA = new Reminder("12-10-2024",
                                                           "lunch",
                                                           new Name("Fiona"));

    // Manually added
    public static final Reminder BREAKFASTLEON = new Reminder("13-10-2024",
                                                              "breakfast",
                                                              new Name("Leon"));

    public static final Reminder GYMTRISTAN = new Reminder("28-11-2023",
                                                           "gym session",
                                                           new Name("Tristan"));

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code ReminderAddressBook} with all the typical reminders.
     */
    public static ReminderAddressBook getTypicalReminderAddressBook() {
        ReminderAddressBook rab = new ReminderAddressBook();
        for (Reminder reminder : getTypicalReminders()) {
            rab.addReminder(reminder);
        }
        return rab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(MEETINGJASON, DINNERKATE, BASKETBALLCARL, JAPANTOM, LUNCHFIONA));
    }
}
