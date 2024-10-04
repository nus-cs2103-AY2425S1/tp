package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_BUY_MEDICATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.person.Name;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddTaskCommand expectedCommand = new AddTaskCommand("Buy medication", new Name("John Doe"));

        // Correct format with `d/` and `p/`
        assertParseSuccess(parser, " d/Buy medication p/John Doe", expectedCommand);
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

        // invalid person name (format is correct but name is invalid)
        assertParseFailure(parser, "addtask d/Buy medication p/R@chel", Name.MESSAGE_CONSTRAINTS);

        // invalid task description and valid person name
        assertParseFailure(parser, "addtask p/!nv@l!d description p/John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        // valid task description and invalid person name
        assertParseFailure(parser, "addtask d/Buy medication p/R@chel", Name.MESSAGE_CONSTRAINTS);
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
}
