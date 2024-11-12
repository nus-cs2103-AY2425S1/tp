package seedu.address.logic.parser.wedding;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEDDING_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.wedding.DeleteWeddingCommand;
import seedu.address.model.wedding.WeddingName;

public class DeleteWeddingCommandParserTest {
    private DeleteWeddingCommandParser parser = new DeleteWeddingCommandParser();

    @Test
    public void parse_validWedding_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + WEDDING_DESC_AMY,
                new DeleteWeddingCommand(AMY_WEDDING));
    }

    @Test
    public void parse_invalidWeddingName_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_WEDDING_DESC, WeddingName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleWeddings_failure() {
        assertParseFailure(parser, WEDDING_DESC_AMY + WEDDING_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEDDING));
    }

    @Test
    public void parse_emptyWeddingName_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_WEDDING_AMY, expectedMessage);
    }

    @Test
    public void parse_emptyString_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }
}
