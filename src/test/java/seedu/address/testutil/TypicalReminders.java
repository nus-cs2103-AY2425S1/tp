package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ClientHub;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;



/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final Reminder ALICE_REMINDER = new ReminderBuilder()
            .withPersonName("Alice Pauline")
            .withDescription("Reminder 1")
            .withDateTime(LocalDate.of(2024, 10, 10),
                    LocalTime.of(11, 11)).build();

    public static final Reminder BENSON_REMINDER = new ReminderBuilder()
            .withPersonName("Benson Meier")
            .withDescription("Reminder 2")
            .withDateTime(LocalDate.of(2024, 11, 10),
                    LocalTime.of(6, 11)).build();

    public static final Reminder CARL_REMINDER = new ReminderBuilder()
            .withPersonName("Carl Kurz")
            .withDescription("Reminder 3")
            .withDateTime(LocalDate.of(2024, 12, 10),
                    LocalTime.of(7, 11)).build();

    public static final Reminder ALICEY_REMINDER = new ReminderBuilder()
            .withPersonName("Alice Potter")
            .withDescription("Reminder 5")
            .withDateTime(LocalDate.of(2024, 12, 10),
                    LocalTime.of(7, 11)).build();

    public static ClientHub getTypicalClientHub() {
        ClientHub ch = new ClientHub();
        for (Person person: getTypicalPersons()) {
            ch.addPerson(person);
        }

        for (Reminder reminder: getTypicalReminder()) {
            ch.addReminder(reminder);
        }
        return ch;
    }

    public static List<Reminder> getTypicalReminder() {
        return new ArrayList<>(Arrays.asList(ALICE_REMINDER, BENSON_REMINDER,
                CARL_REMINDER, ALICEY_REMINDER));
    }
}
