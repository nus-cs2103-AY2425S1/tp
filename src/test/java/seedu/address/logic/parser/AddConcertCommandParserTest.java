package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_COACHECLLA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COACHELLA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddConcertCommand;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertDate;
import seedu.address.testutil.ConcertBuilder;

public class AddConcertCommandParserTest {
    private AddConcertCommandParser parser = new AddConcertCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Concert expectedConcert = new ConcertBuilder(COACHELLA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_COACHELLA
                + ADDRESS_DESC_COACHELLA + DATE_DESC_COACHECLLA, new AddConcertCommand(
                        expectedConcert));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedConcertString = NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA
                + DATE_DESC_COACHECLLA;

        // multiple names
        assertParseFailure(parser, NAME_DESC_COACHELLA + validExpectedConcertString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_COACHELLA + validExpectedConcertString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_COACHECLLA + validExpectedConcertString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser, NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA
                + DATE_DESC_COACHECLLA + validExpectedConcertString, Messages
                        .getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS,
                                PREFIX_DATE));

        // invald value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedConcertString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedConcertString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validExpectedConcertString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // valid followed by invalid

        // invalid name
        assertParseFailure(parser, validExpectedConcertString + INVALID_NAME_DESC, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid address
        assertParseFailure(parser, validExpectedConcertString + INVALID_ADDRESS_DESC, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid date
        assertParseFailure(parser, validExpectedConcertString + INVALID_DATE_DESC, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddConcertCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_COACHELLA + ADDRESS_DESC_COACHELLA
                + DATE_DESC_COACHECLLA, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_COACHELLA + VALID_ADDRESS_COACHELLA
                + DATE_DESC_COACHECLLA, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA
                + VALID_DATE_COACHELLA, expectedMessage);

        // all missing
        assertParseFailure(parser, VALID_NAME_COACHELLA + VALID_ADDRESS_COACHELLA
                + VALID_DATE_COACHELLA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ADDRESS_DESC_COACHELLA
                + DATE_DESC_COACHECLLA, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_COACHELLA + INVALID_ADDRESS_DESC
                + DATE_DESC_COACHECLLA, Address.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA + INVALID_DATE_DESC,
                ConcertDate.MESSAGE_CONSTRAINTS);

        // two invalid, only first reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_ADDRESS_DESC + DATE_DESC_COACHECLLA,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA
                + DATE_DESC_COACHECLLA, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddConcertCommand.MESSAGE_USAGE));
    }
}
