package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FEES_PAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkPaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FeesPaidByStudent;

public class MarkPaidCommandParserTest {
    private final MarkPaidCommandParser parser = new MarkPaidCommandParser();

    // Test valid input
    @Test
    public void parse_validArgs_returnsMarkPaidCommand() throws Exception {
        // Test input with valid index and payment
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PAYMENT + VALID_FEES_PAID;
        MarkPaidCommand expectedCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, new FeesPaidByStudent(VALID_FEES_PAID));
        MarkPaidCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    // Test missing payment prefix
    @Test
    public void parse_missingPaymentPrefix_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " ";
        assertThrows(ParseException.class, () ->
                parser.parse(userInput), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE));
    }

    // Test invalid index format
    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "invalidIndex " + PREFIX_PAYMENT + VALID_FEES_PAID;
        assertThrows(ParseException.class, () ->
                parser.parse(userInput), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE));
    }

    // Test duplicate payment prefixes
    @Test
    public void parse_duplicatePaymentPrefix_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_PAYMENT + VALID_FEES_PAID + " " + PREFIX_PAYMENT + VALID_FEES_PAID;
        assertThrows(ParseException.class, () ->
                parser.parse(userInput), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE));
    }
}

