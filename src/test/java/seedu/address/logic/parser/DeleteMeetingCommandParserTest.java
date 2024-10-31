package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_DATE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_TITLE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_DATE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_TITLE_ADMIRALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

public class DeleteMeetingCommandParserTest {

    private DeleteMeetingCommandParser parser = new DeleteMeetingCommandParser();
    private final String validMeetingTitle = "Project Meeting";
    private final String validMeetingDate = "01-01-2024";
    private final String emptyMeetingTitle = "";
    private final String emptyMeetingDate = "";

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = MEETING_TITLE_DESC_ADMIRALTY + " " + MEETING_DATE_DESC_ADMIRALTY;
        MeetingTitle validMeetingTitle = new MeetingTitle(VALID_MEETING_TITLE_ADMIRALTY);
        MeetingDate validMeetingDate = new MeetingDate(VALID_MEETING_DATE_ADMIRALTY);
        DeleteMeetingCommand expectedCommand = new DeleteMeetingCommand(validMeetingTitle, validMeetingDate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsPresentWithExtraPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);
        String userInput = MEETING_TITLE_DESC_ADMIRALTY + " " + MEETING_DATE_DESC_ADMIRALTY + NAME_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyMeetingTitle_failure() {
        // Test for empty meeting title
        String userInput = DeleteMeetingCommand.COMMAND_WORD + " " + PREFIX_MEETING_TITLE + emptyMeetingTitle + " "
                + PREFIX_MEETING_DATE + validMeetingDate;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyMeetingDate_failure() {
        // Test for empty meeting date
        String userInput = DeleteMeetingCommand.COMMAND_WORD + " " + PREFIX_MEETING_TITLE + validMeetingTitle + " "
                + PREFIX_MEETING_DATE + emptyMeetingDate;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);

        // No meeting title
        assertParseFailure(parser, DeleteMeetingCommand.COMMAND_WORD + " "
                + PREFIX_MEETING_DATE + validMeetingDate, expectedMessage);

        // No meeting date
        assertParseFailure(parser, DeleteMeetingCommand.COMMAND_WORD + " "
                + PREFIX_MEETING_TITLE + validMeetingTitle, expectedMessage);

        // No parameters at all
        assertParseFailure(parser, DeleteMeetingCommand.COMMAND_WORD, expectedMessage);
    }
}
