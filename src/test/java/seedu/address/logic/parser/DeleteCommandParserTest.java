package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "S1234567A" and "T0111335I" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        NricMatchesPredicate predicate = new NricMatchesPredicate(new Nric(VALID_NRIC_BOB));
        DeleteCommand expectedDeleteCommand = new DeleteCommand(predicate);
        assertParseSuccess(parser, VALID_NRIC_BOB , expectedDeleteCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //empty NRIC
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        //invalid NRIC
        assertParseFailure(parser, INVALID_NRIC, Nric.MESSAGE_CONSTRAINTS);
    }
}
