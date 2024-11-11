package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnarchiveCommand;

public class UnarchiveCommmandParserTest {

    private UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1", new UnarchiveCommand(List.of(INDEX_FIRST_PERSON)));
    }

    @Test
    public void parse_multipleValidArgs_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1 2 3",
                new UnarchiveCommand(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
    }
}
