package seedu.address.logic.parser.meetup;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_FROM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_FROM_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_TO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETUP_TO_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_ADDED_BUYER_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_FROM_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_INFO_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_NAME_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_NAME_DESC_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_TO_DESC_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_ADDED_PERSON_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_FROM_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_FROM_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_NAME_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_NAME_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_TO_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_TO_PITCH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETUP;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.meetup.EditCommand;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;
import seedu.address.testutil.meetup.EditMeetUpDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ADDED_BUYER_EMPTY = " " + PREFIX_ADDED_BUYER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_MEETUP_NAME_PITCH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_MEETUP_NOT_EDITED);

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
        assertParseFailure(parser, "1 v/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_MEETUP_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_MEETUP_FROM_DESC, From.MESSAGE_CONSTRAINTS); // invalid from
        assertParseFailure(parser, "1" + INVALID_MEETUP_FROM_DESC_TWO, From.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_MEETUP_TO_DESC, To.MESSAGE_CONSTRAINTS); // invalid to
        assertParseFailure(parser, "1" + INVALID_MEETUP_TO_DESC_TWO, To.MESSAGE_CONSTRAINTS);

        // invalid name followed by valid info, from and to
        assertParseFailure(parser, "1" + INVALID_MEETUP_NAME_DESC
                + VALID_MEETUP_INFO_PITCH + VALID_MEETUP_FROM_PITCH
                + VALID_MEETUP_TO_PITCH, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEETUP;
        String userInput = targetIndex.getOneBased() + MEETUP_NAME_DESC_NETWORKING + MEETUP_INFO_DESC_NETWORKING
                + MEETUP_FROM_DESC_NETWORKING + MEETUP_TO_DESC_NETWORKING + MEETUP_ADDED_BUYER_DESC_NETWORKING;

        EditCommand.EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder()
                .withName(VALID_MEETUP_NAME_NETWORKING)
                .withInfo(VALID_MEETUP_INFO_NETWORKING)
                .withFrom(VALID_MEETUP_FROM_NETWORKING)
                .withTo(VALID_MEETUP_TO_NETWORKING)
                .withAddedBuyer(VALID_MEETUP_ADDED_PERSON_BETTY)
                .build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsButNameAndInfoSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEETUP;
        String userInput = targetIndex.getOneBased()
                + MEETUP_FROM_DESC_NETWORKING + MEETUP_TO_DESC_NETWORKING + MEETUP_ADDED_BUYER_DESC_NETWORKING;

        EditCommand.EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder()
                .withFrom(VALID_MEETUP_FROM_NETWORKING)
                .withTo(VALID_MEETUP_TO_NETWORKING)
                .withAddedBuyer(VALID_MEETUP_ADDED_PERSON_BETTY)
                .build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        Index targetIndex = INDEX_FIRST_MEETUP;
        String userInput = targetIndex.getOneBased() + INVALID_MEETUP_NAME_DESC + MEETUP_NAME_DESC_PITCH;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + MEETUP_NAME_DESC_PITCH + INVALID_MEETUP_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + MEETUP_NAME_DESC_NETWORKING + MEETUP_INFO_DESC_NETWORKING
                + MEETUP_FROM_DESC_NETWORKING + MEETUP_TO_DESC_NETWORKING + MEETUP_NAME_DESC_NETWORKING
                + MEETUP_INFO_DESC_NETWORKING + MEETUP_FROM_DESC_NETWORKING + MEETUP_TO_DESC_NETWORKING;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_INFO, PREFIX_TO, PREFIX_FROM));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_MEETUP_NAME_DESC + MEETUP_NAME_DESC_PITCH
                + INVALID_MEETUP_FROM_DESC + INVALID_MEETUP_TO_DESC + INVALID_MEETUP_NAME_DESC + MEETUP_NAME_DESC_PITCH
                + INVALID_MEETUP_FROM_DESC + INVALID_MEETUP_TO_DESC;


        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_TO, PREFIX_FROM));
    }
}
