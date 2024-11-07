package seedu.address.logic.parser.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.AssignTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TaskAssignmentParserUtilTest {

    @Test
    public void parseTaskCommand_validInput_success() throws Exception {
        String args = "1 2 3";

        TaskAssignmentParserUtil.ParsedCommandData parsedData =
                TaskAssignmentParserUtil.parseTaskCommand(args, AssignTaskCommand.MESSAGE_USAGE);

        // Verify parsed person index and task indexes
        assertEquals(INDEX_FIRST, parsedData.personIndex);
        assertEquals(Set.of(INDEX_SECOND, INDEX_THIRD), parsedData.taskIndexes);
    }

    @Test
    public void parseTaskCommand_missingArguments_throwsParseException() {
        String args = "1";

        // Assert that a ParseException is thrown due to missing task indexes
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), ()
                        -> TaskAssignmentParserUtil.parseTaskCommand(args, AssignTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseTaskCommand_invalidPersonIndex_throwsParseException() {
        String args = "a 1 2";

        // Assert that a ParseException is thrown due to invalid person index
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), ()
                        -> TaskAssignmentParserUtil.parseTaskCommand(args, AssignTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseTaskCommand_invalidTaskIndex_throwsParseException() {
        String args = "1 a 2";

        // Assert that a ParseException is thrown due to invalid task index
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), () ->
                        TaskAssignmentParserUtil.parseTaskCommand(args, AssignTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseTaskCommand_emptyArgs_throwsParseException() {
        String args = "";

        // Assert that a ParseException is thrown for empty arguments
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), ()
                        -> TaskAssignmentParserUtil.parseTaskCommand(args, AssignTaskCommand.MESSAGE_USAGE));
    }
}
