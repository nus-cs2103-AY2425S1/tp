package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_BUY_MEDICATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTaskCommand;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index index1 = Index.fromOneBased(1);
        AddTaskCommand expectedCommand = new AddTaskCommand(index1, "Buy medication");

        // Correct format with `d/` and `p/`
        assertParseSuccess(parser, "1 d/Buy medication", expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // missing task description
        assertParseFailure(parser, NAME_DESC_BOB, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTaskCommand.MESSAGE_USAGE));

        // missing person name
        assertParseFailure(parser, VALID_TASK_DESCRIPTION_BUY_MEDICATION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid task description and valid index
        assertParseFailure(parser, "addtask 1 p/!nv@l!d description",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        // valid task description and invalid index
        assertParseFailure(parser, "addtask a d/Buy medication",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_duplicatePrefixes_failure() {
        String validTaskString = VALID_TASK_DESCRIPTION_BUY_MEDICATION + NAME_DESC_BOB;

        // multiple task descriptions
        assertParseFailure(parser, VALID_TASK_DESCRIPTION_BUY_MEDICATION + validTaskString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        // multiple person names
        assertParseFailure(parser, validTaskString + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTaskDescription_failure() {
        assertParseFailure(parser, "1 d/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
