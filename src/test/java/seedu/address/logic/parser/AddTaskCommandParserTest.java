package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTaskDescription(VALID_TASK_DESCRIPTION_PROJECT)
                .withTaskDeadline(VALID_TASK_DEADLINE).build();

        assertParseSuccess(parser, NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                new AddTaskCommand(new Name(VALID_NAME_BOB), expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                expectedMessage);

        // missing task prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TASK_DESCRIPTION_PROJECT + TASK_DEADLINE_DESC_BOB,
                expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TASK_DESCRIPTION_PROJECT + VALID_TASK_DEADLINE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // empty task description
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TASK_DESC + TASK_DEADLINE_DESC_BOB,
                TaskDescription.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + INVALID_DEADLINE_DESC,
                TaskDeadline.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                "random text " + NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedFields_failure() {
        String validTaskString = NAME_DESC_BOB + TASK_DESCRIPTION_DESC_BOB + TASK_DEADLINE_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple tasks
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_AMY + validTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DESCRIPTION));

        // multiple deadlines
        assertParseFailure(parser, validTaskString + " d/2024-11-30",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DEADLINE));
    }
}
