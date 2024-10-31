package seedu.address.logic.commands.reminder;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;


/**
 * Contains helper methods for testing reminder commands.
 */
public class ReminderCommandTestUtil {

    public static final String VALID_NAME_A = "Amy Bee";
    public static final String VALID_NAME_B = "Bob Choo";
    public static final String VALID_REMINDER_DESCRIPTION_A = "Call client about project";
    public static final String VALID_REMINDER_DESCRIPTION_B = "Send email to team";
    public static final String VALID_DATETIME_A = "2021-10-10 10:00";
    public static final String VALID_DATETIME_B = "2030-12-12 11:00";

    public static final String REMINDER_DESC_A = " " + PREFIX_DESCRIPTION + VALID_REMINDER_DESCRIPTION_A;
    public static final String REMINDER_DESC_B = " " + PREFIX_DESCRIPTION + VALID_REMINDER_DESCRIPTION_B;
    public static final String DATETIME_DESC_A = " " + PREFIX_DATE_TIME + VALID_DATETIME_A;
    public static final String DATETIME_DESC_B = " " + PREFIX_DATE_TIME + VALID_DATETIME_B;

    public static final String INVALID_REMINDER_DESC = " " + PREFIX_DESCRIPTION + "This description is way too long "
            + "and exceeds the character limit set in the application settings which is meant to keep reminders short.";

}
