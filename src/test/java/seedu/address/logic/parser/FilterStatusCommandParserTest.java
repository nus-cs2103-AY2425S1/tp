package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.model.person.StatusContainsKeywordsPredicate;

public class FilterStatusCommandParserTest {
    private final FilterStatusCommandParser parser = new FilterStatusCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterStatusCommand() {
        // no leading and trailing whitespaces
        FilterStatusCommand expectedFilterStatusCommand =
            new FilterStatusCommand(new StatusContainsKeywordsPredicate(Arrays.asList("Applied", "Hired")));
        assertParseSuccess(parser, "Applied Hired", expectedFilterStatusCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Applied \n \t Hired  \t", expectedFilterStatusCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // single invalid status
        assertParseFailure(parser, "Recruited", "Invalid status: Recruited. Valid statuses are: "
            + String.join(", ", FilterStatusCommandParser.VALID_STATUSES));

        // multiple invalid statuses
        assertParseFailure(parser, "Applied Recruited", "Invalid status: Recruited. Valid statuses are: "
            + String.join(", ", FilterStatusCommandParser.VALID_STATUSES));

        // multiple invalid statuses
        assertParseFailure(parser, "Fired Screened", "Invalid status: Fired. Valid statuses are: "
            + String.join(", ", FilterStatusCommandParser.VALID_STATUSES));
    }

    @Test
    public void parse_mixedValidAndInvalidArgs_throwsParseException() {
        // valid and invalid statuses mixed
        assertParseFailure(parser, "Applied Recruited Hired", "Invalid status: Recruited. Valid statuses are: "
            + String.join(", ", FilterStatusCommandParser.VALID_STATUSES));

        // another case with mixed valid and invalid statuses
        assertParseFailure(parser, "Screening Promoted", "Invalid status: Promoted. Valid statuses are: "
            + String.join(", ", FilterStatusCommandParser.VALID_STATUSES));
    }
}
