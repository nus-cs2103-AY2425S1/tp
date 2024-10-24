package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListAbsenteesCommand;
import seedu.address.model.person.AttendanceList;
import seedu.address.model.person.DateAbsentPredicate;

public class ListAbsenteesCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ListAbsenteesCommand.MESSAGE_USAGE);
    private static final String MESSAGE_DATE_CONSTRAINTS = "Date must be in the format: "
            + AttendanceList.DATE_TIME_FORMAT;

    private ListAbsenteesCommandParser parser = new ListAbsenteesCommandParser();

    @Test
    public void parse_validArgs_success() {
        ListAbsenteesCommand command = new ListAbsenteesCommand(
                new DateAbsentPredicate(LocalDateTime.of(2024, 1, 1, 12, 0)));

        // no leading and trailing whitespaces
        // NOTE: there is one single space in the front because ArgumentTokenizer::tokenize always sees the
        // first character as part of the preamble; putting the space prevents the prefix to be mistakenly
        // recognized as part of the preamble.
        assertParseSuccess(parser, " d/01/01/2024 12:00", command);

        // multiple whitespaces
        assertParseSuccess(parser, "\n d/01/01/2024 12:00 \t ", command);
    }

    public void parse_missingField_failure() {
        // empty
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);

        // no prefix
        assertParseFailure(parser, "01/01/2024 12:00", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicateField_failure() {
        assertParseFailure(parser, " d/01/01/2024 12:00 d/08/01/2024 12:00",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_invalidDate_failure() {
        // wrong date part
        assertParseFailure(parser, " d/01-01-2024 12:00", MESSAGE_DATE_CONSTRAINTS);

        // missing hour and minute
        assertParseFailure(parser, " d/01/01/2024", MESSAGE_DATE_CONSTRAINTS);
    }
}
