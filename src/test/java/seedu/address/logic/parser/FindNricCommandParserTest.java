package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindNricCommand;
import seedu.address.model.person.NricMatchesPredicate;

public class FindNricCommandParserTest {

    private FindNricCommandParser parser = new FindNricCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindNricCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindNricCommand expectedFindNricCommand =
                new FindNricCommand(new NricMatchesPredicate(ALICE.getNric()));
        assertParseSuccess(parser, ALICE.getNric().value, expectedFindNricCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "     " + ALICE.getNric().value + "      ", expectedFindNricCommand);
    }
}
