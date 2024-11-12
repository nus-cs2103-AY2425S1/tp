package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.volunteercommands.VolunteerRemoveDateCommand;

public class VolunteerRemoveDateCommandParserTest {

    private static final String VALID_INDEX = " " + VOLUNTEER_PREFIX_INDEX + "1";
    private static final String VALID_DATE = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21";
    private static final String VALID_MULTIPLE_DATES = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21, 2024-10-22";

    private VolunteerRemoveDateCommandParser parser = new VolunteerRemoveDateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = Index.fromOneBased(1);

        // Single date
        assertParseSuccess(parser, VALID_INDEX + VALID_DATE,
                new VolunteerRemoveDateCommand(targetIndex, "2024-10-21"));

        // Multiple dates
        assertParseSuccess(parser, VALID_INDEX + VALID_MULTIPLE_DATES,
                new VolunteerRemoveDateCommand(targetIndex, "2024-10-21, 2024-10-22"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VolunteerRemoveDateCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, "1" + VALID_DATE, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_INDEX + "2024-10-21", expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, "1 2024-10-21", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index
        assertParseFailure(parser, " " + VOLUNTEER_PREFIX_INDEX + "0" + VALID_DATE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(parser, " " + VOLUNTEER_PREFIX_INDEX + "-1" + VALID_DATE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));

        // invalid date format
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024/10/21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));

        // invalid dates in list
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-13-45, 2024-10-21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate index prefix
        assertParseFailure(parser, VALID_INDEX + VALID_INDEX + VALID_DATE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_INDEX));

        // duplicate date prefix
        assertParseFailure(parser, VALID_INDEX + VALID_DATE + VALID_DATE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_AVAILABLE_DATE));

        // duplicate both prefixes
        assertParseFailure(parser, VALID_INDEX + VALID_DATE + VALID_INDEX + VALID_DATE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_INDEX, VOLUNTEER_PREFIX_AVAILABLE_DATE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // non-empty preamble
        assertParseFailure(parser, "some random text" + VALID_INDEX + VALID_DATE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleDates_success() {
        Index targetIndex = Index.fromOneBased(1);

        // Multiple valid dates
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-10-22, 2024-10-23",
                new VolunteerRemoveDateCommand(targetIndex, "2024-10-21, 2024-10-22, 2024-10-23"));

        // Dates in different months
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-11-22",
                new VolunteerRemoveDateCommand(targetIndex, "2024-10-21, 2024-11-22"));

        // Dates in different years
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-12-31, 2025-01-01",
                new VolunteerRemoveDateCommand(targetIndex, "2024-12-31, 2025-01-01"));

        // Extra spaces after comma - should normalize
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-12-21,    2024-11-22",
                new VolunteerRemoveDateCommand(targetIndex, "2024-12-21, 2024-11-22"));

        // No space after comma - should normalize
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21,2024-10-22",
                new VolunteerRemoveDateCommand(targetIndex, "2024-10-21, 2024-10-22"));

        // Extra spaces before and after comma - should normalize
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21  ,  2024-10-22",
                new VolunteerRemoveDateCommand(targetIndex, "2024-10-21, 2024-10-22"));
    }

    @Test
    public void parse_invalidMultipleDates_failure() {
        // Mix of valid and invalid dates
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-13-45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));

        // Wrong separator
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21; 2024-10-22",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));

        // Internal spaces in dates
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024 -10-21, 2024-10-22",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));

        // Duplicate dates in list
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-10-21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE));
    }
}
