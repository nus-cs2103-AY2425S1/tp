package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;

public class AddAssignmentCommandParserTest {

    private final AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_validArgs_returnsAddAssignmentCommand() throws Exception {
        String userInput = " " + PREFIX_NAME + "Assignment 1 " + PREFIX_DUEDATE + "2024-09-09 1200";

        AddAssignmentCommand command = parser.parse(userInput);

        Assignment expectedAssignment = new Assignment("Assignment 1", LocalDateTime.now());
        assertEquals(new AddAssignmentCommand(expectedAssignment), command);
    }

    @Test
    public void parse_emptyTitle_throwsParseException() throws Exception {
        String userInput = " " + PREFIX_NAME + " " + PREFIX_DUEDATE + "2024-09-09 1200";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        // No name provided
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_DUEDATE + "2024-09-09 1200"));

        // No due date
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_NAME + "Assignment 1"));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        // Invalid due date
        assertThrows(ParseException.class, () ->
                parser.parse(PREFIX_NAME + "Assignment 1 " + PREFIX_DUEDATE + "2024-09-09 12"));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing prefix for name
        assertThrows(ParseException.class, () -> parser.parse("Assignment 1"));
    }
}
