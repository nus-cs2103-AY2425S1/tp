package seedu.address.logic.commands.reminder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;


/**
 * Contains helper methods for testing reminder commands.
 */
public class ReminderCommandTestUtil {

    // Reminder for Alice Pauline (used in EditCommandTest)
    public static final String DEFAULT_REMINDER_TIME = "2024-10-10 11:11";
    public static final String DEFAULT_REMINDER_DESCRIPTION = "Reminder 1";
    public static final Reminder DEFAULT_REMINDER = new Reminder("Alice Pauline",
            LocalDateTime.parse(DEFAULT_REMINDER_TIME, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            new ReminderDescription(DEFAULT_REMINDER_DESCRIPTION));
    public static final Set<Reminder> DEFAULT_REMINDER_SET = new HashSet<>(Collections.singleton(DEFAULT_REMINDER));

    // Reminder for Bob Charlie (used in EditCommandTest)
    public static final String DEFAULT_REMINDER_TIME_2 = "2024-10-10 11:11";
    public static final String DEFAULT_REMINDER_DESCRIPTION_2 = "Reminder 2";
    public static final Reminder DEFAULT_REMINDER_2 = new Reminder("Benson Meier",
            LocalDateTime.parse(DEFAULT_REMINDER_TIME_2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            new ReminderDescription(DEFAULT_REMINDER_DESCRIPTION_2));
    public static final Set<Reminder> DEFAULT_REMINDER_SET_2 = new HashSet<>(Collections.singleton(DEFAULT_REMINDER_2));

}
