package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHTAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.person.Grade.MESSAGE_SCORE_CONSTRAINTS;
import static seedu.address.model.person.Grade.MESSAGE_WEIGHTAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
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

        // Float input (2dp)
        assertParseSuccess(parser, "15 n/Midterm s/95.00 w/35.00", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE);

        // Less arguments
        assertParseFailure(parser, "15 n/Midterm s/95.6", errorMessage);

        // Wrong arguments structure
        assertParseFailure(parser, "15 Midterm 95.6 1", errorMessage);
        assertParseFailure(parser, "Adam n/Midterm s/95.6 w/30", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "15 n/Midterm s/A w/30", MESSAGE_SCORE_CONSTRAINTS);
        assertParseFailure(parser, "15 n/Midterm s/95.6 w/FM", MESSAGE_WEIGHTAGE_CONSTRAINTS);
        assertParseFailure(parser, "15 n/Midterm s/95.6 w/90.000", MESSAGE_WEIGHTAGE_CONSTRAINTS);

        // Too many arguments
        assertParseFailure(parser, "15 n/Midterm s/95.000 w/35.000 w/777",
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEIGHTAGE));
        assertParseFailure(parser, "15 n/Midterm s/95.000 s/35.000 w/777",
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCORE));
        assertParseFailure(parser, "15 n/Midterm n/Finals s/35.000 w/777",
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
