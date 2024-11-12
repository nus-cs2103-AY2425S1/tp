package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BETA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listcommands.ListProjectMembersCommand;
import seedu.address.model.assignment.AssignmentProjectPredicate;

public class ListProjectMembersCommandParserTest {

    private ListProjectMembersCommandParser parser = new ListProjectMembersCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListProjectMembersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ListProjectMembersCommand expectedListCommand =
                new ListProjectMembersCommand(new AssignmentProjectPredicate(VALID_PROJECT_NAME_BETA));
        assertParseSuccess(parser, PROJECT_NAME_DESC_BETA, expectedListCommand);
    }

}
