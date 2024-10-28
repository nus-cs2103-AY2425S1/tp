package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETING_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETING_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_BUYER;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_DATE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_SELLER;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_TITLE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE;
import static seedu.address.logic.commands.CommandTestUtil.TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_DATE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_TITLE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CONDO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.testutil.MeetingBuilder;

public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Meeting expectedMeeting = new MeetingBuilder()
                .withMeetingTitle(VALID_MEETING_TITLE_ADMIRALTY)
                .withMeetingDate(VALID_MEETING_DATE_ADMIRALTY)
                .withBuyer(VALID_PHONE_AMY)
                .withSeller(VALID_PHONE_BOB)
                .withType(VALID_TYPE_CONDO)
                .withPostalCode(VALID_POSTALCODE_ADMIRALTY)
                .build();
        // whitespace only preamble
        assertParseSuccess(parser, MEETING_TITLE_DESC_ADMIRALTY + MEETING_DATE_DESC_ADMIRALTY + MEETING_BUYER
                + MEETING_SELLER + TYPE + POSTALCODE,
                new AddMeetingCommand(expectedMeeting));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessageMeetingTitle = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING_TITLE);
        String expectedMessageMeetingDate = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING_DATE);

        // duplicate name prefix
        assertParseFailure(parser, MEETING_TITLE_DESC_ADMIRALTY + MEETING_TITLE_DESC_ADMIRALTY
                + MEETING_DATE_DESC_ADMIRALTY + MEETING_BUYER
                + MEETING_SELLER + TYPE + POSTALCODE, expectedMessageMeetingTitle);

        // duplicate phone prefix
        assertParseFailure(parser, MEETING_TITLE_DESC_ADMIRALTY + MEETING_DATE_DESC_ADMIRALTY
                        + MEETING_DATE_DESC_ADMIRALTY + MEETING_BUYER
                + MEETING_SELLER + TYPE + POSTALCODE, expectedMessageMeetingDate);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // missing meeting title prefix
        assertParseFailure(parser, VALID_MEETING_TITLE_ADMIRALTY + MEETING_DATE_DESC_ADMIRALTY,
                expectedMessage);

        // missing meeting date prefix
        assertParseFailure(parser, MEETING_TITLE_DESC_ADMIRALTY + VALID_MEETING_TITLE_ADMIRALTY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MEETING_TITLE_ADMIRALTY + VALID_MEETING_TITLE_ADMIRALTY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid meeting title
        assertParseFailure(parser, INVALID_MEETING_TITLE_DESC + MEETING_DATE_DESC_ADMIRALTY
                        + MEETING_BUYER
                        + MEETING_SELLER + TYPE + POSTALCODE,
                MeetingTitle.MESSAGE_CONSTRAINTS);

        // invalid meeting date
        assertParseFailure(parser, MEETING_TITLE_DESC_ADMIRALTY + INVALID_MEETING_DATE_DESC
                        + MEETING_BUYER
                        + MEETING_SELLER + TYPE + POSTALCODE,
                MeetingDate.MESSAGE_CONSTRAINTS);
        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEETING_TITLE_DESC + INVALID_MEETING_DATE_DESC
                        + MEETING_BUYER
                        + MEETING_SELLER + TYPE + POSTALCODE,
                MeetingTitle.MESSAGE_CONSTRAINTS);
    }
}
