package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateTaskCommand;
import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;
import seedu.address.testutil.UpdateTaskDescriptorBuilder;

public class UpdateTaskCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTaskCommand.MESSAGE_USAGE);

    private UpdateTaskCommandParser parser = new UpdateTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // non-empty preamble specified
        assertParseFailure(parser, "goonmaster" + NAME_DESC_AMY + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, NAME_DESC_AMY + TASK_INDEX_DESC, UpdateTaskCommand.MESSAGE_NOT_UPDATED);

        // nothing specified
        assertParseFailure(parser, "   ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TASK_INDEX_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid task index
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_TASK_INDEX, MESSAGE_INVALID_INDEX);

        // invalid task description
        assertParseFailure(parser, NAME_DESC_AMY + TASK_INDEX_DESC + INVALID_TASK_DESC,
                TaskDescription.MESSAGE_CONSTRAINTS);

        // invalid task deadline
        assertParseFailure(parser, NAME_DESC_AMY + TASK_INDEX_DESC + INVALID_DEADLINE_DESC,
                TaskDeadline.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                NAME_DESC_AMY + TASK_INDEX_DESC + INVALID_TASK_DESC + INVALID_DEADLINE_DESC,
                TaskDescription.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NAME_DESC_AMY + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY + TASK_DEADLINE_DESC_AMY;

        UpdateTaskDescriptor descriptor = new UpdateTaskDescriptorBuilder()
                .withTaskDescription(VALID_TASK_DESCRIPTION_AMY).withTaskDeadline(VALID_TASK_DEADLINE_AMY).build();
        UpdateTaskCommand expectedCommand =
                new UpdateTaskCommand(new Name(VALID_NAME_AMY), Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // task description
        Name targetName = new Name(VALID_NAME_BOB);
        String userInput = NAME_DESC_BOB + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY;
        UpdateTaskDescriptor descriptor =
                new UpdateTaskDescriptorBuilder().withTaskDescription(VALID_TASK_DESCRIPTION_AMY).build();
        UpdateTaskCommand expectedCommand =
                new UpdateTaskCommand(targetName, Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task deadline
        userInput = NAME_DESC_BOB + TASK_INDEX_DESC + TASK_DEADLINE_DESC_AMY;
        descriptor = new UpdateTaskDescriptorBuilder().withTaskDeadline(VALID_TASK_DEADLINE_AMY).build();
        expectedCommand = new UpdateTaskCommand(targetName, Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = NAME_DESC_AMY + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY + INVALID_TASK_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DESCRIPTION));

        // invalid followed by valid
        userInput = NAME_DESC_AMY + TASK_INDEX_DESC + INVALID_DEADLINE_DESC + TASK_DEADLINE_DESC_AMY;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DEADLINE));

        // multiple optional valid fields repeated
        userInput = NAME_DESC_AMY + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY + TASK_DESCRIPTION_DESC_BOB
                + TASK_DEADLINE_DESC_AMY + TASK_DEADLINE_DESC_BOB + TASK_DEADLINE_DESC_AMY;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE));

        // multiple valid fields repeated
        userInput = NAME_DESC_AMY + NAME_DESC_BOB + TASK_INDEX_DESC + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_AMY;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_TASK_INDEX));

        // multiple invalid values
        userInput = NAME_DESC_AMY + TASK_INDEX_DESC + INVALID_TASK_DESC + INVALID_DEADLINE_DESC
                + INVALID_TASK_DESC + INVALID_DEADLINE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE));
    }
}
