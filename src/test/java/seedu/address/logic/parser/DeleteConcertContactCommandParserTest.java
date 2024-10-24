package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteConcertContactCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteConcertContactCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteConcertContactCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteConcertContactCommandParserTest {

    private DeleteConcertContactCommandParser parser = new DeleteConcertContactCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " " + PREFIX_PERSON + "1 " + PREFIX_CONCERT + INDEX_FIRST_CONCERT.getOneBased(),
                new DeleteConcertContactCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CONCERT));
    }

    @Test
    public void parse_invalidPersonArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PERSON + "a " + PREFIX_CONCERT + INDEX_FIRST_CONCERT.getOneBased(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteConcertContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidConcertArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PERSON + "1 " + PREFIX_CONCERT + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteConcertContactCommand.MESSAGE_USAGE));
    }
}
