package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SearchAppointmentCommand;
import seedu.address.logic.commands.SearchBirthdayCommand;
import seedu.address.logic.commands.SearchPolicyCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class SearchCommandParserTest {
    private final SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void emptyArgs_throwsParseException() {
        // empty string as input
        assertThrows(ParseException.class, () -> parser.parse(""),
                "Expected ParseException for empty input.");

        // whitespaces as input
        assertThrows(ParseException.class, () -> parser.parse("   "),
                "Expected ParseException for whitespace input.");
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // parse unrecognised prefix ( not a/, b/ , p/)
        assertThrows(ParseException.class, () -> parser.parse("z/ 2024-10-10"),
                "Expected ParseException for invalid prefix.");

    }

    @Test
    public void parse_validBirthdayPrefix_success() throws ParseException, CommandException {
        String date = "2023-10-22";
        Command command = parser.parse("b/" + date);
        assertEquals(new SearchBirthdayCommand(date), command,
                "Expected SearchBirthdayCommand to be returned.");


    }

    @Test
    public void parse_validAppointmentPrefix_success() throws CommandException, ParseException {
        String dateTime = "2023-10-22 23:00";
        Command command = parser.parse("a/" + dateTime);
        assertEquals(new SearchAppointmentCommand(dateTime), command,
                "Expected SearchAppointmentCommand to be returned.");
    }

    @Test
    public void parse_validPolicyPrefix_success() throws ParseException, CommandException {
        String policyName = "Life Insurance";
        Command command = parser.parse("p/" + policyName);
        assertEquals(new SearchPolicyCommand(policyName), command,
                "Expected SearchPolicyCommand to be returned.");

    }

    @Test
    public void parse_missingArguments_throwsParseException() {
        // Test with missing arguments after prefixes
        assertThrows(ParseException.class, () -> parser.parse("b/"),
                "Expected ParseException for missing birthday argument.");
        assertThrows(ParseException.class, () -> parser.parse("a/"),
                "Expected ParseException for missing appointment argument.");
        assertThrows(ParseException.class, () -> parser.parse("p/"),
                "Expected ParseException for missing policy name argument.");
    }

    @Test
    public void parse_extraWhitespace_success() throws Exception {
        // Test with extra whitespaces
        String date = "2023-10-22";
        Command command = parser.parse("  b/   " + date + "   ");
        assertEquals(new SearchBirthdayCommand(date), command,
                "Expected SearchBirthdayCommand with extra whitespaces to be parsed successfully.");
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        // Test with multiple prefixes
        assertThrows(ParseException.class, () -> parser.parse("b/2023-10-22 a/2023-10-22 10:00"),
                "Expected ParseException for input with multiple prefixes.");
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        // Test with completely unknown input
        assertThrows(ParseException.class, () -> parser.parse("unknown"),
                "Expected ParseException for unknown input.");
    }

    @Test
    public void parse_validPolicyPrefixWithExtraSpaces_success() throws Exception {
        String policyName = "   Health Insurance   ";
        Command command = parser.parse("p/" + policyName);
        assertEquals(new SearchPolicyCommand(policyName.trim()), command,
                "Expected SearchPolicyCommand with trimmed policy name.");
    }
}
