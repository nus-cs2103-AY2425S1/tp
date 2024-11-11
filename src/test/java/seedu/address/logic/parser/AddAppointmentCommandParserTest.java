package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FROM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FROM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TO_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAppointmentCommand;

public class AddAppointmentCommandParserTest {

    private final AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String input = PREAMBLE_WHITESPACE + "1" + DATE_DESC_AMY + FROM_DESC_AMY + TO_DESC_AMY;

        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON,
                LocalDate.parse(VALID_DATE_AMY),
                LocalTime.parse(VALID_START_TIME_AMY),
                LocalTime.parse(VALID_END_TIME_AMY));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String validExpectedAppointmentString = "1" + DATE_DESC_AMY + FROM_DESC_AMY + TO_DESC_AMY;

        // multiple dates
        assertParseFailure(parser, validExpectedAppointmentString + DATE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple start times
        assertParseFailure(parser, validExpectedAppointmentString + FROM_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FROM));

        // multiple end times
        assertParseFailure(parser, validExpectedAppointmentString + TO_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TO));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE);

        // missing index
        assertParseFailure(parser, DATE_DESC_AMY + FROM_DESC_AMY + TO_DESC_AMY,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, "1 " + VALID_DATE_AMY + FROM_DESC_AMY + TO_DESC_AMY,
                expectedMessage);

        // missing from prefix
        assertParseFailure(parser, "1" + DATE_DESC_AMY + " " + VALID_START_TIME_AMY + TO_DESC_AMY,
                expectedMessage);

        // missing to prefix
        assertParseFailure(parser, "1" + DATE_DESC_AMY + FROM_DESC_AMY + " " + VALID_END_TIME_AMY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "1 " + VALID_DATE_AMY + " " + VALID_START_TIME_AMY + " " + VALID_END_TIME_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid index
        assertParseFailure(parser, "a" + DATE_DESC_AMY + FROM_DESC_AMY + TO_DESC_AMY,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + FROM_DESC_AMY + TO_DESC_AMY,
                Messages.MESSAGE_INVALID_DATE_FORMAT);

        // invalid start time
        assertParseFailure(parser, "1" + DATE_DESC_AMY + INVALID_FROM_DESC + TO_DESC_AMY,
                Messages.MESSAGE_INVALID_TIME_FORMAT);

        // invalid end time
        assertParseFailure(parser, "1" + DATE_DESC_AMY + FROM_DESC_AMY + INVALID_TO_DESC,
                Messages.MESSAGE_INVALID_TIME_FORMAT);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + FROM_DESC_AMY + INVALID_TO_DESC,
                Messages.MESSAGE_INVALID_DATE_FORMAT);
    }
}
