package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.volunteercommands.VolunteerAddDateCommand;


public class VolunteerAddDateCommandParserTest {

    private static final String VALID_INDEX = " " + VOLUNTEER_PREFIX_INDEX + "1";
    private static final String VALID_DATE = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21";
    private static final String VALID_MULTIPLE_DATES = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21, 2024-10-22";

    private VolunteerAddDateCommandParser parser = new VolunteerAddDateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Single date
        Index targetIndex = Index.fromOneBased(1);
        assertParseSuccess(parser, VALID_INDEX + VALID_DATE,
                new VolunteerAddDateCommand(targetIndex, "2024-10-21"));

        // Multiple dates
        assertParseSuccess(parser, VALID_INDEX + VALID_MULTIPLE_DATES,
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE);

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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(parser, " " + VOLUNTEER_PREFIX_INDEX + "-1" + VALID_DATE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // invalid date format
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024/10/21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // invalid dates in list
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-13-45, 2024-10-21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));
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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleDates_success() {
        Index targetIndex = Index.fromOneBased(1);

        // Multiple valid dates
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-10-22, 2024-10-23",
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22, 2024-10-23"));

        // Dates in different months
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-11-22",
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-11-22"));

        // Dates in different years
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-12-31, 2025-01-01",
                new VolunteerAddDateCommand(targetIndex, "2024-12-31, 2025-01-01"));


        // Consistent single space after comma is valid
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-10-22, 2024-10-23",
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22, 2024-10-23"));

        // Trimmed dates with single space after comma should still work
        String dateWithSpaces = "2024-10-21, 2024-10-22"; // has exactly one space after comma
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + dateWithSpaces,
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22"));

        // Extra spaces after comma - should normalize to standard format
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-12-21,    2024-11-22",
                new VolunteerAddDateCommand(targetIndex, "2024-12-21, 2024-11-22"));

        // No space after comma - should normalize to standard format
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21,2024-10-22",
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22"));

        // Extra spaces before and after comma - should normalize to standard format
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21  ,  2024-10-22",
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22"));

        // Inconsistent spacing after comma is valid
        assertParseSuccess(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21,    2024-10-22  ,2024-10-23",
                new VolunteerAddDateCommand(targetIndex, "2024-10-21, 2024-10-22, 2024-10-23"));
    }

    @Test
    public void parse_invalidMultipleDates_failure() {
        // Mix of valid and invalid dates
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-13-45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // Wrong separator
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21; 2024-10-22",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // Missing comma
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21 2024-10-22",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // Extra commas
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21,, 2024-10-22",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // Duplicate dates in list
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10-21, 2024-10-21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // Extra spaces within a date
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024 -10-21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024- 10-21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024-10 -21",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

        // Multiple spacing issues combined
        assertParseFailure(parser, VALID_INDEX + " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                        + "2024 -10-21,  2024- 10-22,2024-10 -23",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddDateCommand.MESSAGE_USAGE));

    }
}
