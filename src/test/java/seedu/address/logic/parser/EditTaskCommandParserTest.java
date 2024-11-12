package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_FIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.editcommands.EditTaskCommand;
import seedu.address.logic.commands.editcommands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.editcommands.EditTaskCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.TaskName;


public class EditTaskCommandParserTest {
    private EditTaskCommandParser parser = new EditTaskCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Index expectedIndex = Index.fromOneBased(1);
        GroupName expectedGroupName = new GroupName(TEAM_FIVE);
        TaskName expectedTaskName = new TaskName("TP");
        Deadline expectedDeadline = new Deadline(LocalDateTime.of(2025, 12, 31, 23, 0));
        EditTaskDescriptor descriptor = new EditTaskDescriptor();
        descriptor.setTaskName(expectedTaskName);
        descriptor.setDeadline(expectedDeadline);
        EditTaskCommand expectedCommand = new EditTaskCommand(expectedGroupName, expectedIndex, descriptor);
        String userInput = " " + PREFIX_GROUP_NAME + TEAM_FIVE + " " + PREFIX_INDEX + "1 "
            + PREFIX_TASK_NAME + "TP " + PREFIX_TASK_DEADLINE + "2025-12-31 2300";
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String userInputMissingIndex = " " + PREFIX_GROUP_NAME + "TeamGamma";
        assertParseFailure(parser, userInputMissingIndex, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditTaskCommand.MESSAGE_USAGE));

        String userInputMissingGroupName = " " + PREFIX_INDEX + "2";
        assertParseFailure(parser, userInputMissingGroupName,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_noFieldsEdited_failure() {
        String userInput = " " + PREFIX_GROUP_NAME + TEAM_FIVE + " " + PREFIX_INDEX + "1 "
            + PREFIX_TASK_NAME + "TP " + PREFIX_TASK_DEADLINE + "2025-12-31 2300 "
            + PREFIX_EMAIL + "e0000000@u.nus.edu";
        assertThrows(ParseException.class, ()->parser.parse(userInput),
            String.format(Messages.MESSAGE_ILLEGAL_PREFIX_USED, EditTaskCommand.MESSAGE_USAGE));
    }

}
