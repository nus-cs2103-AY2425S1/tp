package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIRED_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIRED_ROLE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIREDROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DesiredRoleCommand;
import seedu.address.model.person.DesiredRole;

public class DesiredRoleCommandParserTest {

    private DesiredRoleCommandParser parser = new DesiredRoleCommandParser();

    @Test
    public void parse_validArgs_returnsDesiredRoleCommand() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DESIREDROLE + VALID_DESIRED_ROLE_AMY;

        DesiredRoleCommand expectedCommand =
                new DesiredRoleCommand(targetIndex, new DesiredRole(VALID_DESIRED_ROLE_AMY));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validArgsForBob_returnsDesiredRoleCommand() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DESIREDROLE + VALID_DESIRED_ROLE_BOB;

        DesiredRoleCommand expectedCommand =
                new DesiredRoleCommand(targetIndex, new DesiredRole(VALID_DESIRED_ROLE_BOB));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // No index
        assertParseFailure(parser, " " + PREFIX_DESIREDROLE + VALID_DESIRED_ROLE_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DesiredRoleCommand.MESSAGE_USAGE));

        // No desired role
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DesiredRoleCommand.MESSAGE_USAGE));

        // No index and no desired role
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DesiredRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid index
        assertParseFailure(parser, "-1 " + PREFIX_DESIREDROLE + VALID_DESIRED_ROLE_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DesiredRoleCommand.MESSAGE_USAGE));

        // Invalid desired role
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_DESIREDROLE + "!",
                DesiredRole.MESSAGE_CONSTRAINTS);
    }
}
