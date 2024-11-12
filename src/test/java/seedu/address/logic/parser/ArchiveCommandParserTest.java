package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ArchiveCommand;


public class ArchiveCommandParserTest {

    private static final String INVALID_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE);

    private final ArchiveCommandParser archiveParser = new ArchiveCommandParser(true);

    private final ArchiveCommandParser unarchiveParser = new ArchiveCommandParser(false);

    @Test
    public void parse_archiveWithIndex_returnsArchiveCommand() {
        assertParseSuccess(archiveParser, "1", new ArchiveCommand(INDEX_FIRST_PERSON, true));
        assertParseSuccess(archiveParser, "2", new ArchiveCommand(INDEX_SECOND_PERSON, true));
    }

    @Test
    public void parse_unarchiveWithIndex_returnsArchiveCommand() {
        assertParseSuccess(unarchiveParser, "1", new ArchiveCommand(INDEX_FIRST_PERSON, false));
        assertParseSuccess(unarchiveParser, "2", new ArchiveCommand(INDEX_SECOND_PERSON, false));
    }

    @Test
    public void parse_archiveWithoutIndex_throwsParseException() {
        assertParseFailure(archiveParser, "", INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_unarchiveWithoutIndex_throwsParseException() {
        assertParseFailure(unarchiveParser, "", INVALID_COMMAND_FORMAT);
    }
}
