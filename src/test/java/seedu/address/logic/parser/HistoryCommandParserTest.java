package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HistoryCommand;
import seedu.address.model.person.Nric;

public class HistoryCommandParserTest {
    private HistoryCommandParser parser = new HistoryCommandParser();

    @Test
    public void parse_validArgs_returnsHistoryCommand() {
        assertParseSuccess(parser, "1", new HistoryCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, HistoryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNric_returnsHistoryCommand() {
        assertParseSuccess(parser, VALID_NRIC_AMY, new HistoryCommand(new Nric(VALID_NRIC_AMY)));
    }
}
