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
        DeleteGradeCommand command = new DeleteGradeCommand(Index.fromOneBased(15), "Midterm");

        // Trailing whitespaces
        assertParseSuccess(parser, "15 Midterm ", command);

        // Leading whitespaces
        assertParseSuccess(parser, " 15 Midterm", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);

        // Less arguments
        assertParseFailure(parser, "15", errorMessage);

        // Wrong arguments structure (Invalid index)
        assertParseFailure(parser, "Alice Midterm", ParserUtil.MESSAGE_INVALID_INDEX);

        // Missing test name
        assertParseFailure(parser, "15 ", errorMessage);

        // Too many arguments
        assertParseFailure(parser, "15 Midterm 17", errorMessage);
    }
}
