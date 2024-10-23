package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortIndividualCommandParserTest {
    private final SortIndividualCommandParser parser = new SortIndividualCommandParser();

    @Test
    public void parse_missingOrder_throwsParseException() {
        // Missing order
        assertThrows(ParseException.class, () -> parser.parse("1 f/Price"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortIndividualCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidField_throwsParseException() {
        // Invalid field
        assertThrows(ParseException.class, () -> parser.parse("1 f/InvalidField o/L"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortIndividualCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidOrder_throwsParseException() {
        // Invalid order
        assertThrows(ParseException.class, () -> parser.parse("1 f/Price o/InvalidOrder"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortIndividualCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate order prefix
        assertThrows(ParseException.class, () -> parser.parse("1 f/Price o/L o/H"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortIndividualCommand.MESSAGE_USAGE));
    }
}
