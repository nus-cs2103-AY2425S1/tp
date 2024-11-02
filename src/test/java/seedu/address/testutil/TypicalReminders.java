package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ReminderAddressBook;
import seedu.address.model.person.Reminder;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder REMINDER_ALICE = new ReminderBuilder()
            .withDate("12-12-2024")
            .withDescription("Mock interview with Alice")
            .withName("Alice Pauline")
            .build();

    public static final Reminder REMINDER_BENSON = new ReminderBuilder()
            .withDate("15-12-2024")
            .withDescription("Networking session with Benson for internship insights")
            .withName("Benson Lin")
            .build();

    public static final Reminder REMINDER_CARL = new ReminderBuilder()
            .withDate("20-12-2024")
            .withDescription("Resume review and feedback session with Carl")
            .withName("Carl Kurz")
            .build();

    public static final Reminder REMINDER_DANIEL = new ReminderBuilder()
            .withDate("22-12-2024")
            .withDescription("Technical interview prep with Daniel")
            .withName("Daniel Meier")
            .build();

    public static final Reminder REMINDER_ELLE = new ReminderBuilder()
            .withDate("30-12-2024")
            .withDescription("Behavioral interview coaching with Elle")
            .withName("Elle Meyer")
            .build();

    public static final Reminder REMINDER_FIONA = new ReminderBuilder()
            .withDate("05-01-2025")
            .withDescription("Practice group case study with Fiona")
            .withName("Fiona Kunz")
            .build();

    public static final Reminder REMINDER_GEORGE = new ReminderBuilder()
            .withDate("10-01-2025")
            .withDescription("Coding assessment practice with George")
            .withName("George Best")
            .build();

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

    /**
     * Returns a list of typical reminders.
     */
    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(REMINDER_ALICE, REMINDER_BENSON, REMINDER_CARL,
                REMINDER_DANIEL, REMINDER_ELLE));
    }
}
