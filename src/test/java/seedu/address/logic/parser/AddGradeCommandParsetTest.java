package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.model.person.Grade;

public class AddGradeCommandParsetTest {
    private AddGradeCommandParser parser = new AddGradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddGradeCommand command = new AddGradeCommand(Index.fromOneBased(15), new Grade("Midterm", 95.0F, 35.0F));

        // Trailing whitespaces
        assertParseSuccess(parser, "15 Midterm 95 35  ", command);

        // Leading whitespaces
        assertParseSuccess(parser, "  15 Midterm 95 35", command);

        // Float input
        assertParseSuccess(parser, "15 Midterm 95.000 35.000", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE);

        // Less arguments
        assertParseFailure(parser, "15 Midterm 95.6", errorMessage);

        // Wrong arguments structure
        assertParseFailure(parser, "Adam Midterm 95.6 30", errorMessage);
        assertParseFailure(parser, "15 Midterm A+ 30", errorMessage);
        assertParseFailure(parser, "15 Midterm 95.6 FM", errorMessage);

        // Too many arguments
        assertParseFailure(parser, "15 Midterm 95.000 35.000 777", errorMessage);
    }
}
