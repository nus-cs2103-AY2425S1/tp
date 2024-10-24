package seedu.address.logic.parser.meetup;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_ADDED_BUYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_FROM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_INFO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_TO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_ADDED_BUYER_DESC_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_FROM_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_FROM_DESC_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_INFO_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_INFO_DESC_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_NAME_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_NAME_DESC_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_TO_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_TO_DESC_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_ADDED_PERSON_ALEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_FROM_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_NAME_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_TO_PITCH;
import static seedu.address.logic.commands.meetup.AddCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.meetup.TypicalMeetUps.PITCH_MEETUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.meetup.AddCommand;
import seedu.address.model.buyer.Name;
import seedu.address.model.meetup.AddedBuyer;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.To;
import seedu.address.testutil.meetup.MeetUpBuilder;

public class AddCommandParserTest {

    private seedu.address.logic.parser.meetup.AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        MeetUp expectedMeetUp = new MeetUpBuilder(PITCH_MEETUP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEETUP_NAME_DESC_PITCH
                + MEETUP_INFO_DESC_PITCH + MEETUP_FROM_DESC_PITCH
                + MEETUP_TO_DESC_PITCH + MEETUP_ADDED_BUYER_DESC_PITCH, new AddCommand(expectedMeetUp));
    }

    @Test
    public void parse_repeatedNonBuyerAddedValue_failure() {
        String validExpectedMeetUpString = MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH + MEETUP_ADDED_BUYER_DESC_PITCH;

        // multiple names
        assertParseFailure(parser, MEETUP_NAME_DESC_NETWORKING + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple infos
        assertParseFailure(parser, MEETUP_INFO_DESC_NETWORKING + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INFO));

        // multiple froms
        assertParseFailure(parser, MEETUP_FROM_DESC_NETWORKING + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FROM));

        // multiple tos
        assertParseFailure(parser, MEETUP_TO_DESC_NETWORKING + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TO));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedMeetUpString + MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                        + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_INFO, PREFIX_FROM, PREFIX_TO));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_MEETUP_NAME_DESC + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid info
        assertParseFailure(parser, INVALID_MEETUP_INFO_DESC + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INFO));

        // invalid from
        assertParseFailure(parser, INVALID_MEETUP_FROM_DESC + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FROM));

        // invalid to
        assertParseFailure(parser, INVALID_MEETUP_TO_DESC + validExpectedMeetUpString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TO));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedMeetUpString + INVALID_MEETUP_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid from
        assertParseFailure(parser, validExpectedMeetUpString + INVALID_MEETUP_FROM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FROM));

        // invalid info
        assertParseFailure(parser, validExpectedMeetUpString + INVALID_MEETUP_INFO_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INFO));

        // invalid to
        assertParseFailure(parser, validExpectedMeetUpString + INVALID_MEETUP_TO_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TO));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_MEETUP_NAME_PITCH + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH + MEETUP_ADDED_BUYER_DESC_PITCH, expectedMessage);

        // missing info prefix
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + VALID_MEETUP_INFO_PITCH
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH + MEETUP_ADDED_BUYER_DESC_PITCH, expectedMessage);

        // missing from prefix
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + VALID_MEETUP_FROM_PITCH + MEETUP_TO_DESC_PITCH + MEETUP_ADDED_BUYER_DESC_PITCH, expectedMessage);

        // missing to prefix
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + VALID_MEETUP_TO_PITCH + MEETUP_ADDED_BUYER_DESC_PITCH, expectedMessage);

        // missing added buyer prefix
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH + VALID_MEETUP_ADDED_PERSON_ALEX, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MEETUP_NAME_PITCH + VALID_MEETUP_INFO_PITCH
                + VALID_MEETUP_FROM_PITCH + VALID_MEETUP_TO_PITCH + VALID_MEETUP_ADDED_PERSON_ALEX, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid name
        assertParseFailure(parser, INVALID_MEETUP_NAME_DESC + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH
                + MEETUP_ADDED_BUYER_DESC_PITCH, Name.MESSAGE_CONSTRAINTS);

        // invalid info
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + INVALID_MEETUP_INFO_DESC
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH
                + MEETUP_ADDED_BUYER_DESC_PITCH, Info.MESSAGE_CONSTRAINTS);

        // invalid from
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + INVALID_MEETUP_FROM_DESC + MEETUP_TO_DESC_PITCH
                + MEETUP_ADDED_BUYER_DESC_PITCH, From.MESSAGE_CONSTRAINTS);

        // invalid to
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + INVALID_MEETUP_TO_DESC
                + MEETUP_ADDED_BUYER_DESC_PITCH, To.MESSAGE_CONSTRAINTS);

        // invalid added buyer
        assertParseFailure(parser, MEETUP_NAME_DESC_PITCH + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH
                + INVALID_MEETUP_ADDED_BUYER_DESC, AddedBuyer.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEETUP_NAME_DESC + MEETUP_INFO_DESC_PITCH
                + MEETUP_FROM_DESC_PITCH + INVALID_MEETUP_TO_DESC
                + MEETUP_ADDED_BUYER_DESC_PITCH, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEETUP_NAME_DESC_PITCH
                + MEETUP_INFO_DESC_PITCH + MEETUP_FROM_DESC_PITCH + MEETUP_TO_DESC_PITCH
                + MEETUP_ADDED_BUYER_DESC_PITCH, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
