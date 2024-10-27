package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;

public class MarkAssignmentCommandParserTest {

    private final MarkAssignmentCommandParser parser = new MarkAssignmentCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAssignmentCommand() throws Exception {
        String userInput = "1 " + PREFIX_NAME + "Assignment 1";

        MarkAssignmentCommand command = parser.parse(userInput);

        Assignment expectedAssignment = new Assignment("Assignment 1", LocalDateTime.now());
        assertEquals(new MarkAssignmentCommand(0, expectedAssignment), command);
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        // No name provided
        assertThrows(ParseException.class, () -> parser.parse("1"));

        // No index
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_NAME + "Assignment"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index
        assertThrows(ParseException.class, () -> parser.parse("a " + PREFIX_NAME + "Assignment 1"));
        assertThrows(ParseException.class, () -> parser.parse("-1 " + PREFIX_NAME + "Assignment 2"));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing prefix for name
        assertThrows(ParseException.class, () -> parser.parse("1 Assignment 1"));
    }
}
