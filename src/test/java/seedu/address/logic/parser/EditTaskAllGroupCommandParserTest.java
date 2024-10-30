package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import seedu.address.logic.commands.editcommands.EditTaskAllGroupCommand;
import seedu.address.logic.commands.editcommands.EditTaskAllGroupCommand.EditTaskDescriptor;
import seedu.address.logic.parser.editcommands.EditTaskAllGroupCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.TaskName;


public class EditTaskAllGroupCommandParserTest {
    private EditTaskAllGroupCommandParser parser = new EditTaskAllGroupCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Index expectedIndex = Index.fromOneBased(1);
        TaskName expectedTaskName = new TaskName("TP");
        Deadline expectedDeadline = new Deadline(LocalDateTime.of(2025, 12, 31, 23, 0));
        EditTaskDescriptor descriptor = new EditTaskDescriptor();
        descriptor.setTaskName(expectedTaskName);
        descriptor.setDeadline(expectedDeadline);
        EditTaskAllGroupCommand expectedCommand = new EditTaskAllGroupCommand(expectedIndex, descriptor);
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_TASK_NAME + "TP " + PREFIX_TASK_DEADLINE
            + "2025-12-31 2300";
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String userInputMissingGroupName = " " + PREFIX_INDEX + "2";
        assertParseFailure(parser, userInputMissingGroupName,
            EditTaskAllGroupCommand.MESSAGE_NOT_EDITED);
    }
    @Test
    public void parse_noFieldsEdited_failure() {
        String userInput = " " + PREFIX_GROUP_NAME + TEAM_FIVE + " " + PREFIX_INDEX + "1 "
            + PREFIX_TASK_NAME + "TP " + PREFIX_TASK_DEADLINE + "2025-12-31 2300 "
            + PREFIX_EMAIL + "e0000000@u.nus.edu";
        assertThrows(ParseException.class, ()->parser.parse(userInput),
            String.format(Messages.MESSAGE_ILLEGAL_PREFIX_USED, EditTaskAllGroupCommand.MESSAGE_USAGE));
    }

}
