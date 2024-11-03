package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ArchiveCommand;

public class ArchiveCommandParserTest {
    private static final String INPUT_MISSING_PREFIX = "archive mybook.json";
    private static final String INPUT_MULTIPLE_PATH = "archive pa/mybook.json pa/mybook2.json";
    private static final String VALID_INPUT = "archive pa/TestValidInput.json";
    private static final ArchiveCommandParser PARSER = new ArchiveCommandParser();

    @Test
    void invalid_input_throwException() {
        assertParseFailure(PARSER, INPUT_MISSING_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
        assertParseFailure(PARSER, INPUT_MULTIPLE_PATH,
                String.format(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PATH)));
    }

    @Test
    void valid_input() throws Exception {
        assertParseSuccess(PARSER, VALID_INPUT,
                new ArchiveCommand(Paths.get("archived", "TestValidInput.json")));
    }
}

