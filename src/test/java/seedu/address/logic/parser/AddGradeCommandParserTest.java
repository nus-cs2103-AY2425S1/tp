package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.model.person.Grade;

public class AddGradeCommandParserTest {
    private AddGradeCommandParser parser = new AddGradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddGradeCommand command = new AddGradeCommand(Index.fromOneBased(15), new Grade("Midterm", 95.0F, 35.0F));

        // Trailing whitespaces
        assertParseSuccess(parser, "15 n/Midterm s/95 w/35  ", command);

        // Leading whitespaces
        assertParseSuccess(parser, "  15 n/Midterm s/95 w/35", command);

        // Float input
        assertParseSuccess(parser, "15 n/Midterm s/95.000 w/35.000", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE);

        // Less arguments
        assertParseFailure(parser, "15 n/Midterm s/95.6", errorMessage);

        // Wrong arguments structure
        assertParseFailure(parser, "15 Midterm 95.6 1", errorMessage);
        assertParseFailure(parser, "Adam n/Midterm s/95.6 w/30", errorMessage);
        assertParseFailure(parser, "15 n/Midterm s/A w/30", errorMessage);
        assertParseFailure(parser, "15 n/Midterm s/95.6 w/FM", errorMessage);

        // Too many arguments
        assertParseFailure(parser, "15 n/Midterm s/95.000 w/35.000 777", errorMessage);
    }
}
