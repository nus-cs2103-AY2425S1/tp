package seedu.address.logic.parser.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.INVALID_TASK_DESC;
import static seedu.address.testutil.TypicalTasks.TASK_DESC_DEADLINE;
import static seedu.address.testutil.TypicalTasks.TASK_DESC_EVENT;
import static seedu.address.testutil.TypicalTasks.TASK_DESC_TODO;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.task.CreateTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;
import seedu.address.testutil.TypicalTasks;

public class CreateTaskCommandParserTest {

    private final CreateTaskCommandParser parser = new CreateTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // CreateTaskCommand with all valid task descriptions
        Set<Task> expectedTasks = Set.copyOf(TypicalTasks.getTypicalTasks());

        CreateTaskCommand expectedCommand = new CreateTaskCommand(new HashSet<>(expectedTasks));

        assertEquals(expectedCommand, parser.parse(TASK_DESC_TODO + TASK_DESC_DEADLINE + TASK_DESC_EVENT));
    }

    @Test
    public void parse_incompleteDescription_failure() {
        // Invalid task with incomplete description
        assertThrows(ParseException.class, Messages.MESSAGE_INCOMPLETE_TASK_DESCRIPTION, ()
                -> parser.parse(INVALID_TASK_DESC));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid command format (empty argument)
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CreateTaskCommand.MESSAGE_USAGE), ()
                -> parser.parse(""));

        // Missing task descriptions (no task prefix)
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CreateTaskCommand.MESSAGE_USAGE), ()
                -> parser.parse("This is a random string"));
    }
}
