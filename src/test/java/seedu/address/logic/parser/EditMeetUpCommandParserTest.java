package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditMeetUpCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.To;
import seedu.address.testutil.EditMeetUpDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.*;

public class EditMeetUpCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetUpCommand.MESSAGE_USAGE);

    private EditMeetUpCommandParser parser = new EditMeetUpCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_MEETUP_NAME_PITCH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditMeetUpCommand.MESSAGE_MEETUP_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + MEETUP_NAME_DESC_PITCH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + MEETUP_NAME_DESC_PITCH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 a/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_MEETUP_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
//        assertParseFailure(parser, "1" + INVALID_MEETUP_INFO_DESC, Info.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_MEETUP_FROM_DESC, From.MESSAGE_CONSTRAINTS); // invalid from
        assertParseFailure(parser, "1" + INVALID_MEETUP_FROM_DESC_TWO, From.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_MEETUP_TO_DESC, To.MESSAGE_CONSTRAINTS); // invalid to
        assertParseFailure(parser, "1" + INVALID_MEETUP_TO_DESC_TWO, To.MESSAGE_CONSTRAINTS);

        // invalid name followed by valid info, from and to
        assertParseFailure(parser, "1" + INVALID_MEETUP_NAME_DESC
                + VALID_MEETUP_INFO_PITCH + VALID_MEETUP_FROM_PITCH
                + VALID_MEETUP_TO_PITCH, Name.MESSAGE_CONSTRAINTS);

//        // multiple invalid values, but only the first invalid value is captured
//        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
//                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEETUP;
        String userInput = targetIndex.getOneBased() + MEETUP_NAME_DESC_NETWORKING + MEETUP_INFO_DESC_NETWORKING
                + MEETUP_FROM_DESC_NETWORKING + MEETUP_TO_DESC_NETWORKING;

        EditMeetUpCommand.EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder()
                .withName(VALID_MEETUP_NAME_NETWORKING)
                .withInfo(VALID_MEETUP_INFO_NETWORKING)
                .withFrom(VALID_MEETUP_FROM_NETWORKING)
                .withTo(VALID_MEETUP_TO_NETWORKING)
                .build();

        EditMeetUpCommand expectedCommand = new EditMeetUpCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsButNameAndInfoSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEETUP;
        String userInput = targetIndex.getOneBased() + MEETUP_FROM_DESC_NETWORKING + MEETUP_TO_DESC_NETWORKING;

        EditMeetUpCommand.EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder()
                .withFrom(VALID_MEETUP_FROM_NETWORKING)
                .withTo(VALID_MEETUP_TO_NETWORKING)
                .build();

        EditMeetUpCommand expectedCommand = new EditMeetUpCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

//    @Test
//    public void parse_multipleRepeatedFields_failure() {
//        // More extensive testing of duplicate parameter detections is done in
//        // AddMeetUpCommandParserTest#parse_repeatedNonTagValue_failure()
//
//        // invalid followed by valid
//        Index targetIndex = INDEX_FIRST_MEETUP;
//        String userInput = targetIndex.getOneBased() + INVALID_MEETUP_NAME_DESC + PHONE_DESC_BOB;
//
//        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
//
//        // valid followed by invalid
//        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;
//
//        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
//
//        // mulltiple valid fields repeated
//        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
//                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
//                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + PERSON_TYPE_DESC_AMY + PERSON_TYPE_DESC_BOB
//                + TAG_DESC_HUSBAND;
//
//        assertParseFailure(parser, userInput,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
//                        PREFIX_PERSON_TYPE));
//
//        // multiple invalid values
//        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
//                + INVALID_PERSON_TYPE_DESC + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
//                + INVALID_PERSON_TYPE_DESC;
//
//        assertParseFailure(parser, userInput,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
//                        PREFIX_PERSON_TYPE));
//    }
}
