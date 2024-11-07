package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentByTgCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.student.TutorialGroup;

class AddAssignmentByTgCommandParserTest {

    private final AddAssignmentByTgCommandParser parser = new AddAssignmentByTgCommandParser();

    @Test
    void parse_allFieldsPresent_success() throws Exception {
        String input = " " + PREFIX_TUTORIAL_GROUP + "T15 "
                + PREFIX_ASSIGNMENT + "Math Quiz "
                + PREFIX_DEADLINE + "2024-10-09";

        AddAssignmentByTgCommand expectedCommand = new AddAssignmentByTgCommand(
                new Assignment(new AssignmentName("Math Quiz"),
                        new Deadline("2024-10-09"),
                        Status.getDefault(),
                        Grade.getDefault()),
                new TutorialGroup("T15"));

        AddAssignmentByTgCommand resultCommand = parser.parse(input);
        assertEquals(expectedCommand, resultCommand);
    }

    @Test
    void parse_missingTutorialGroup_throwsParseException() {
        String input = " " + PREFIX_ASSIGNMENT + "Math Quiz "
                + PREFIX_DEADLINE + "2024-10-09";

        assertThrows(ParseException.class, () -> parser.parse(input),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentByTgCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingAssignment_throwsParseException() {
        String input = " " + PREFIX_TUTORIAL_GROUP + "T15 "
                + PREFIX_DEADLINE + "2024-10-09";

        assertThrows(ParseException.class, () -> parser.parse(input),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentByTgCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingDeadline_throwsParseException() {
        String input = " " + PREFIX_TUTORIAL_GROUP + "T15 "
                + PREFIX_ASSIGNMENT + "Math Quiz";

        assertThrows(ParseException.class, () -> parser.parse(input),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentByTgCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        String input = " " + PREFIX_TUTORIAL_GROUP + "T15 "
                + PREFIX_ASSIGNMENT + "Math Quiz "
                + PREFIX_DEADLINE + "09-10-2024";

        assertThrows(ParseException.class, () -> parser.parse(input),
                Deadline.MESSAGE_CONSTRAINTS);
    }
}
