package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VOLUNTEER_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_DATE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_DATE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_EMAIL_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_PHONE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEER_PHONE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.volunteercommands.VolunteerNewCommand;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerDates;
import seedu.address.testutil.VolunteerBuilder;

public class VolunteerNewCommandParserTest {
    private VolunteerNewCommandParser parser = new VolunteerNewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Volunteer expectedVolunteer = new VolunteerBuilder()
                .withName(VALID_VOLUNTEER_NAME_ALICE)
                .withPhone(VALID_VOLUNTEER_PHONE_ALICE)
                .withEmail(VALID_VOLUNTEER_EMAIL_ALICE)
                .withAvailableDate(VALID_VOLUNTEER_DATE_ALICE)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                new VolunteerNewCommand(expectedVolunteer));
    }

    @Test
    public void parse_duplicateFields_failure() {
        String validCommand = VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE;

        // duplicate name prefix - rejected
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_BOB + VOLUNTEER_NAME_DESC_ALICE
                        + VOLUNTEER_PHONE_DESC_ALICE + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_NAME));

        // duplicate phone prefix - rejected
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_BOB
                        + VOLUNTEER_PHONE_DESC_ALICE + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_PHONE));

        // duplicate email prefix - rejected
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_BOB + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_EMAIL));

        // duplicate date prefix - rejected
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_BOB + VOLUNTEER_DATE_DESC_ALICE,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_AVAILABLE_DATE));

        // all fields duplicate - rejected
        assertParseFailure(parser, validCommand + " " + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                        VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerNewCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_VOLUNTEER_NAME_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VALID_VOLUNTEER_PHONE_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VALID_VOLUNTEER_EMAIL_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + VALID_VOLUNTEER_DATE_ALICE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_VOLUNTEER_NAME_ALICE + VALID_VOLUNTEER_PHONE_ALICE
                        + VALID_VOLUNTEER_EMAIL_ALICE + VALID_VOLUNTEER_DATE_ALICE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_VOLUNTEER_NAME_DESC + VOLUNTEER_PHONE_DESC_ALICE
                + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + INVALID_VOLUNTEER_PHONE_DESC
                + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                + INVALID_VOLUNTEER_EMAIL_DESC + VOLUNTEER_DATE_DESC_ALICE, Email.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                + VOLUNTEER_EMAIL_DESC_ALICE + INVALID_VOLUNTEER_DATE_DESC, VolunteerDates.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_VOLUNTEER_NAME_DESC + INVALID_VOLUNTEER_PHONE_DESC
                + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + VOLUNTEER_DATE_DESC_ALICE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerNewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleDates_success() {
        // Two valid dates
        String multipleDatesDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21, 2024-10-22";
        Volunteer expectedVolunteerTwoDates = new VolunteerBuilder()
                .withName(VALID_VOLUNTEER_NAME_ALICE)
                .withPhone(VALID_VOLUNTEER_PHONE_ALICE)
                .withEmail(VALID_VOLUNTEER_EMAIL_ALICE)
                .withAvailableDate("2024-10-21, 2024-10-22")
                .build();

        assertParseSuccess(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + multipleDatesDesc,
                new VolunteerNewCommand(expectedVolunteerTwoDates));

        // Multiple valid dates
        String manyDatesDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE
                + "2024-10-21, 2024-10-22, 2024-10-23, 2024-10-24";
        Volunteer expectedVolunteerManyDates = new VolunteerBuilder()
                .withName(VALID_VOLUNTEER_NAME_ALICE)
                .withPhone(VALID_VOLUNTEER_PHONE_ALICE)
                .withEmail(VALID_VOLUNTEER_EMAIL_ALICE)
                .withAvailableDate("2024-10-21, 2024-10-22, 2024-10-23, 2024-10-24")
                .build();

        assertParseSuccess(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + manyDatesDesc,
                new VolunteerNewCommand(expectedVolunteerManyDates));
    }

    @Test
    public void parse_invalidMultipleDates_failure() {
        // One valid date, one invalid date
        String mixedDatesDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21, 2024-13-45";
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + mixedDatesDesc,
                VolunteerDates.MESSAGE_CONSTRAINTS);

        // Multiple invalid dates
        String allInvalidDatesDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-13-45, 2024-15-67, 2024-00-00";
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + allInvalidDatesDesc,
                VolunteerDates.MESSAGE_CONSTRAINTS);

        // Invalid date format (wrong separator)
        String wrongSeparatorDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21; 2024-10-22";
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + wrongSeparatorDesc,
                VolunteerDates.MESSAGE_CONSTRAINTS);

        // Missing comma between dates
        String missingCommaDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21 2024-10-22";
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + missingCommaDesc,
                VolunteerDates.MESSAGE_CONSTRAINTS);

        // Duplicate dates in list
        String duplicateDatesDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21, 2024-10-21";
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + duplicateDatesDesc, (
                                new VolunteerDuplicateDateException("2024-10-21")).getMessage());

        // Extra commas
        String extraCommasDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-21,, 2024-10-22";
        assertParseFailure(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + extraCommasDesc,
                VolunteerDates.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleDatesEdgeCases_success() {
        // Same date for different months
        String sameDayDiffMonthDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-10-15, 2024-11-15";
        Volunteer expectedVolunteerSameDay = new VolunteerBuilder()
                .withName(VALID_VOLUNTEER_NAME_ALICE)
                .withPhone(VALID_VOLUNTEER_PHONE_ALICE)
                .withEmail(VALID_VOLUNTEER_EMAIL_ALICE)
                .withAvailableDate("2024-10-15, 2024-11-15")
                .build();

        assertParseSuccess(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + sameDayDiffMonthDesc,
                new VolunteerNewCommand(expectedVolunteerSameDay));

        // Dates spanning multiple years
        String multiYearDesc = " " + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2024-12-31, 2025-01-01";
        Volunteer expectedVolunteerMultiYear = new VolunteerBuilder()
                .withName(VALID_VOLUNTEER_NAME_ALICE)
                .withPhone(VALID_VOLUNTEER_PHONE_ALICE)
                .withEmail(VALID_VOLUNTEER_EMAIL_ALICE)
                .withAvailableDate("2024-12-31, 2025-01-01")
                .build();

        assertParseSuccess(parser, VOLUNTEER_NAME_DESC_ALICE + VOLUNTEER_PHONE_DESC_ALICE
                        + VOLUNTEER_EMAIL_DESC_ALICE + multiYearDesc,
                new VolunteerNewCommand(expectedVolunteerMultiYear));
    }
}
