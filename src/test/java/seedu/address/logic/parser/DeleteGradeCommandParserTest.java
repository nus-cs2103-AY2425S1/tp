package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Grade.MESSAGE_TEST_NAME_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteGradeCommand;

public class DeleteGradeCommandParserTest {
    private DeleteGradeCommandParser parser = new DeleteGradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DeleteGradeCommand command = new DeleteGradeCommand(Index.fromOneBased(15), "Midterm");

        // Trailing whitespaces
        assertParseSuccess(parser, "15 n/Midterm ", command);

        // Leading whitespaces
        assertParseSuccess(parser, " 15 n/Midterm", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);

        // Less arguments
        assertParseFailure(parser, "15", errorMessage);

        // Wrong arguments structure (Invalid index)
        assertParseFailure(parser, "Alice n/Midterm", ParserUtil.MESSAGE_INVALID_INDEX);

        // Missing test name
        assertParseFailure(parser, "15 ", errorMessage);
        assertParseFailure(parser, "15 n/", MESSAGE_TEST_NAME_CONSTRAINTS);

        // Too many arguments
        assertParseFailure(parser, "15 Midterm 17", errorMessage);
        assertParseFailure(parser, "15 n/Midterm n/17", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
