package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFeesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Fees;

class AddFeesCommandParserTest {

    private final AddFeesCommandParser parser = new AddFeesCommandParser();

    @Test
    void parse_validArgs_returnsAddFeesCommand() throws ParseException {
        String input = "1 " + PREFIX_PAYMENT + "200";
        AddFeesCommand command = parser.parse(input);

        assertEquals(new AddFeesCommand(Index.fromOneBased(1), new Fees("200")), command);
    }

    @Test
    void parse_validArgsWithSpace_returnsAddFeesCommand() throws ParseException {
        String input = "1    " + PREFIX_PAYMENT + "   200  ";
        AddFeesCommand command = parser.parse(input);

        assertEquals(new AddFeesCommand(Index.fromOneBased(1), new Fees("200")), command);
    }

    @Test
    void parse_indexOutOfRange_throwsParseException() throws ParseException {
        String input = "1 " + PREFIX_PAYMENT + "1000000000";
        AddFeesCommand command = parser.parse(input);

        assertEquals(new AddFeesCommand(Index.fromOneBased(1), new Fees("1000000000")), command);
    }

    @Test
    void parse_missingPrefix_throwsParseException() {
        String input = "1 200";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_wrongPrefix_throwsParseException() {
        String input = "1 " + "ff" + PREFIX_PAYMENT + "200";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        String input = "a " + PREFIX_PAYMENT + "200"; // Non-integer index
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_missingValueForPrefix_throwsParseException() {
        String input = "1 " + PREFIX_PAYMENT; // Missing value after the prefix
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_duplicatePrefixes_throwsParseException() {
        String input = "1 " + PREFIX_PAYMENT + "200 " + PREFIX_PAYMENT + "300"; // Duplicate prefix
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_negativeFees_throwsParseException() {
        String input = "1 " + PREFIX_PAYMENT + "-100"; // Negative fee amount
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_zeroFees_returnsAddFeesCommand() throws ParseException {
        String input = "1 " + PREFIX_PAYMENT + "0"; // Fee amount is zero
        AddFeesCommand command = parser.parse(input);

        assertEquals(new AddFeesCommand(Index.fromOneBased(1), new Fees("0")), command);
    }

    @Test
    void parse_validInputExtraSpace_returnsAddFeesCommand() throws ParseException {
        String input = "   1     " + PREFIX_PAYMENT + "200   "; // Extra whitespace
        AddFeesCommand command = parser.parse(input);

        assertEquals(new AddFeesCommand(Index.fromOneBased(1), new Fees("200")), command);
    }

    @Test
    void parse_invalidFeeFormat_throwsParseException() {
        String input = "1 " + PREFIX_PAYMENT + "twenty"; // Non-numeric fee
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}

