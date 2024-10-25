package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.DIDDY;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnmarkPresentAllCommand;
import seedu.address.model.student.TutorialGroup;

public class UnmarkPresentAllCommandParserTest {

    private UnmarkPresentAllCommandParser parser = new UnmarkPresentAllCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // no leading and trailing whitespaces
        LocalDate date = LocalDate.of(2024, 10, 23);
        UnmarkPresentAllCommand expectedUnmarkPresentAllCommand = new UnmarkPresentAllCommand(
                DIDDY.getTutorialGroup(), date);
        assertParseSuccess(parser, " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_DIDDY + " " + PREFIX_DATE + date,
                expectedUnmarkPresentAllCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid tutorial group
        LocalDate date = LocalDate.of(2024, 10, 23);
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_GROUP + "G0! " + PREFIX_DATE + date,
                TutorialGroup.MESSAGE_CONSTRAINTS);

        // invalid date
        String dateConstraints = "Invalid date format. Please use YYYY-MM-DD.";
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_DIDDY + " "
                + PREFIX_DATE + "2 Jan 2024", dateConstraints);
    }

    @Test
    public void parse_missingFields_throwsParseException() {
        // missing tutorial group
        LocalDate date = LocalDate.of(2024, 10, 23);
        assertParseFailure(parser, " " + PREFIX_DATE + date,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkPresentAllCommand.MESSAGE_USAGE));

        // missing date
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_DIDDY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkPresentAllCommand.MESSAGE_USAGE));
    }
}
