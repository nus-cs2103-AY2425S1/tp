package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Valid values for volunteers
    public static final String VALID_VOLUNTEER_NAME_ALICE = "Alice Wong";
    public static final String VALID_VOLUNTEER_PHONE_ALICE = "91234567";
    public static final String VALID_VOLUNTEER_EMAIL_ALICE = "alicewong@example.com";
    public static final String VALID_VOLUNTEER_DATE_ALICE = "2023-05-10";

    public static final String VALID_VOLUNTEER_NAME_BOB = "Bob Tan";
    public static final String VALID_VOLUNTEER_PHONE_BOB = "98765432";
    public static final String VALID_VOLUNTEER_EMAIL_BOB = "bobtan@example.com";
    public static final String VALID_VOLUNTEER_DATE_BOB = "2023-06-15";

    // Valid values for events
    public static final String VALID_EVENT_NAME_BEACH_CLEANUP = "Beach Cleanup";
    public static final String VALID_EVENT_DATE_BEACH_CLEANUP = "2023-10-15";
    public static final String VALID_EVENT_START_TIME_BEACH_CLEANUP = "08:00";
    public static final String VALID_EVENT_END_TIME_BEACH_CLEANUP = "12:00";
    public static final String VALID_EVENT_LOCATION_BEACH_CLEANUP = "East Coast Park";
    public static final String VALID_EVENT_DESCRIPTION_BEACH_CLEANUP = "Annual beach cleaning event";

    public static final String VALID_EVENT_NAME_CHARITY_RUN = "Charity Run";
    public static final String VALID_EVENT_DATE_CHARITY_RUN = "2023-11-05";
    public static final String VALID_EVENT_START_TIME_CHARITY_RUN = "06:00";
    public static final String VALID_EVENT_END_TIME_CHARITY_RUN = "11:00";
    public static final String VALID_EVENT_LOCATION_CHARITY_RUN = "Marina Bay";
    public static final String VALID_EVENT_DESCRIPTION_CHARITY_RUN = "5km run to raise funds for charity";

    // CLI descriptor strings for volunteers
    public static final String VOLUNTEER_NAME_DESC_ALICE = " " + VOLUNTEER_PREFIX_NAME + VALID_VOLUNTEER_NAME_ALICE;
    public static final String VOLUNTEER_PHONE_DESC_ALICE = " " + VOLUNTEER_PREFIX_PHONE + VALID_VOLUNTEER_PHONE_ALICE;
    public static final String VOLUNTEER_EMAIL_DESC_ALICE = " " + VOLUNTEER_PREFIX_EMAIL + VALID_VOLUNTEER_EMAIL_ALICE;
    public static final String VOLUNTEER_DATE_DESC_ALICE = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
            + VALID_VOLUNTEER_DATE_ALICE;
    public static final String VOLUNTEER_NAME_DESC_BOB = " " + VOLUNTEER_PREFIX_NAME + VALID_VOLUNTEER_NAME_BOB;
    public static final String VOLUNTEER_PHONE_DESC_BOB = " " + VOLUNTEER_PREFIX_PHONE + VALID_VOLUNTEER_PHONE_BOB;
    public static final String VOLUNTEER_EMAIL_DESC_BOB = " " + VOLUNTEER_PREFIX_EMAIL + VALID_VOLUNTEER_EMAIL_BOB;
    public static final String VOLUNTEER_DATE_DESC_BOB = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
            + VALID_VOLUNTEER_DATE_BOB;

    // CLI descriptor strings for events
    public static final String EVENT_NAME_DESC_BEACH_CLEANUP = " " + EVENT_PREFIX_NAME + VALID_EVENT_NAME_BEACH_CLEANUP;
    public static final String EVENT_DATE_DESC_BEACH_CLEANUP = " " + EVENT_PREFIX_DATE + VALID_EVENT_DATE_BEACH_CLEANUP;
    public static final String EVENT_START_TIME_DESC_BEACH_CLEANUP = " " + EVENT_PREFIX_START_TIME
            + VALID_EVENT_START_TIME_BEACH_CLEANUP;
    public static final String EVENT_END_TIME_DESC_BEACH_CLEANUP = " " + EVENT_PREFIX_END_TIME
            + VALID_EVENT_END_TIME_BEACH_CLEANUP;
    public static final String EVENT_LOCATION_DESC_BEACH_CLEANUP = " " + EVENT_PREFIX_LOCATION
            + VALID_EVENT_LOCATION_BEACH_CLEANUP;
    public static final String EVENT_DESCRIPTION_DESC_BEACH_CLEANUP = " " + EVENT_PREFIX_DESCRIPTION
            + VALID_EVENT_DESCRIPTION_BEACH_CLEANUP;

    public static final String EVENT_NAME_DESC_CHARITY_RUN = " " + EVENT_PREFIX_NAME + VALID_EVENT_NAME_CHARITY_RUN;
    public static final String EVENT_DATE_DESC_CHARITY_RUN = " " + EVENT_PREFIX_DATE + VALID_EVENT_DATE_CHARITY_RUN;
    public static final String EVENT_START_TIME_DESC_CHARITY_RUN = " " + EVENT_PREFIX_START_TIME
            + VALID_EVENT_START_TIME_CHARITY_RUN;
    public static final String EVENT_END_TIME_DESC_CHARITY_RUN = " " + EVENT_PREFIX_END_TIME
            + VALID_EVENT_END_TIME_CHARITY_RUN;
    public static final String EVENT_LOCATION_DESC_CHARITY_RUN = " " + EVENT_PREFIX_LOCATION
            + VALID_EVENT_LOCATION_CHARITY_RUN;
    public static final String EVENT_DESCRIPTION_DESC_CHARITY_RUN = " " + EVENT_PREFIX_DESCRIPTION
            + VALID_EVENT_DESCRIPTION_CHARITY_RUN;

    // Invalid values
    public static final String INVALID_VOLUNTEER_NAME_DESC = " " + VOLUNTEER_PREFIX_NAME
            + "James&"; // '&' not allowed in names
    public static final String INVALID_VOLUNTEER_PHONE_DESC = " " + VOLUNTEER_PREFIX_PHONE
            + "911a"; // 'a' not allowed in phones
    public static final String INVALID_VOLUNTEER_EMAIL_DESC = " " + VOLUNTEER_PREFIX_EMAIL
            + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_VOLUNTEER_DATE_DESC = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
            + "32/13/2023"; // Invalid date
    public static final String INVALID_EVENT_NAME_DESC = " " + EVENT_PREFIX_NAME
            + "Beach Cleanup@"; // '@' not allowed in event names
    public static final String INVALID_EVENT_DATE_DESC = " " + EVENT_PREFIX_DATE
            + "31/02/2023"; // Invalid date
    public static final String INVALID_EVENT_START_TIME_DESC = " " + EVENT_PREFIX_START_TIME + "30:00"; // Invalid time
    public static final String INVALID_EVENT_END_TIME_DESC = " " + EVENT_PREFIX_END_TIME + "99:99"; // Invalid time
    public static final String INVALID_EVENT_LOCATION_DESC = " " + EVENT_PREFIX_LOCATION
            + ""; // Empty location not allowed
    public static final String INVALID_EVENT_DESCRIPTION_DESC = " " + EVENT_PREFIX_DESCRIPTION
            + "#Cleanup"; // '#' not allowed in description

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // commented out for future reference when this test case is needed

    //    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    //    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    //
    //    static {
    //        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
    //                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //                .withTags(VALID_TAG_FRIEND).build();
    //        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    //    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }
}
