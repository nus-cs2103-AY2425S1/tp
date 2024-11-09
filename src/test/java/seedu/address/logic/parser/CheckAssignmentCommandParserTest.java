package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CheckAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;

public class CheckAssignmentCommandParserTest {

    private final CheckAssignmentCommandParser parser = new CheckAssignmentCommandParser();

    @Test
    public void parse_validArgs_returnsAddAssignmentCommand() throws Exception {
        String userInput = " " + PREFIX_NAME + "Assignment 1 ";

        CheckAssignmentCommand command = parser.parse(userInput);

        Assignment expectedAssignment = new Assignment("Assignment 1", LocalDateTime.now());
        assertEquals(new CheckAssignmentCommand(expectedAssignment), command);
    }

    @Test
    public void parse_emptyTitle_throwsParseException() throws Exception {
        String userInput = " " + PREFIX_NAME;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        // No name provided
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing prefix for name
        assertThrows(ParseException.class, () -> parser.parse("Assignment 1"));
    }
}
