package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDANCE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ATTENDANCE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_REASON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;

public class AddAttendanceParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE);

    private AddAttendanceCommandParser parser = new AddAttendanceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ATTENDANCE_DESC_AMY, MESSAGE_INVALID_INDEX);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ATTENDANCE_DESC_AMY, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + ATTENDANCE_DESC_AMY, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ATTENDANCE_DESC, AbsentDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // both fields are filled
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ATTENDANCE_DESC_AMY;
        AddAttendanceCommand expectedCommand = new AddAttendanceCommand(targetIndex,
                new AbsentDate("20-10-2024"), new AbsentReason("Sick"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // absent reason field is empty
        userInput = targetIndex.getOneBased() + " " + PREFIX_ABSENT_DATE + "20-10-2024" + " "
                + PREFIX_ABSENT_REASON + ""; // No reason
        expectedCommand = new AddAttendanceCommand(targetIndex,
                new AbsentDate("20-10-2024"), new AbsentReason(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void absentDateSpecified_success() {
        // absent reason is missing
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ABSENT_DATE + "20-10-2024"; // No reason
        AddAttendanceCommand expectedCommand = new AddAttendanceCommand(targetIndex,
                new AbsentDate("20-10-2024"), new AbsentReason(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
