package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LINK_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PET;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnlinkCommand;

public class UnlinkCommandParserTest {

    private UnlinkCommandParser parser = new UnlinkCommandParser();

    @Test
    public void parse_validArgs_returnsUnlinkCommand() {
        assertParseSuccess(parser, "o1 " + PREFIX_TO + "p1", new UnlinkCommand(INDEX_FIRST_OWNER,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PET))));
        assertParseSuccess(parser, "o2 " + PREFIX_TO + "p1 " + PREFIX_TO + "p2",
                new UnlinkCommand(INDEX_SECOND_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET, INDEX_SECOND_PET))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_TO + "p02", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "o15a " + PREFIX_TO + "p12a", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "o15a " + PREFIX_TO + "p12 " + PREFIX_TO + "p15a", MESSAGE_INVALID_INDEX);

        // Current implementation of index disallowed non owner and non pet indexes
        assertParseFailure(parser, "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "o15 " + PREFIX_TO + "t12", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "s15 " + PREFIX_TO + "p12", MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "o15 " + PREFIX_TO + "o12", String.format(MESSAGE_INVALID_LINK_COMMAND,
                UnlinkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "p15 " + PREFIX_TO + "o12", String.format(MESSAGE_INVALID_LINK_COMMAND,
                UnlinkCommand.MESSAGE_USAGE));
    }
}
