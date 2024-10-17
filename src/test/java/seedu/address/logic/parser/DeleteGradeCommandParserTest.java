package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGradeCommand;

public class DeleteGradeCommandParserTest {
    private DeleteGradeCommandParser parser = new DeleteGradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DeleteGradeCommand command = new DeleteGradeCommand(Index.fromOneBased(15), Index.fromOneBased(17));

        // Trailing whitespaces
        assertParseSuccess(parser, "15 17  ", command);

        // Leading whitespaces
        assertParseSuccess(parser, "  15 17", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);

        // Less arguments
        assertParseFailure(parser, "15", errorMessage);

        // Wrong arguments structure
        assertParseFailure(parser, "Alice 17", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "15 Midterm", ParserUtil.MESSAGE_INVALID_INDEX);

        // Too many arguments
        assertParseFailure(parser, "15 16 17", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
