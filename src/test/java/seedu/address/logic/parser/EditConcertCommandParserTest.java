package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_COACHECLLA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COACHELLA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CONCERT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditConcertCommand;
import seedu.address.logic.commands.EditConcertCommand.EditConcertDescriptor;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.ConcertDate;
import seedu.address.testutil.EditConcertDescriptorBuilder;

public class EditConcertCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, EditConcertCommand.MESSAGE_USAGE);

    private EditConcertCommandParser parser = new EditConcertCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_COACHELLA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditConcertCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_COACHELLA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_COACHELLA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, ConcertDate.MESSAGE_CONSTRAINTS); // invalid date

        // invalid address followed by valid date
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC + DATE_DESC_COACHECLLA,
                Address.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_ADDRESS_DESC
                + VALID_DATE_COACHELLA, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CONCERT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA
                + DATE_DESC_COACHECLLA;

        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder()
                .withName(VALID_NAME_COACHELLA)
                .withAddress(VALID_ADDRESS_COACHELLA)
                .withDate(VALID_DATE_COACHELLA).build();
        EditConcertCommand expectedCommand = new EditConcertCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CONCERT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA;

        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder()
                .withName(VALID_NAME_COACHELLA)
                .withAddress(VALID_ADDRESS_COACHELLA)
                .build();
        EditConcertCommand expectedCommand = new EditConcertCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CONCERT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COACHELLA;
        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder()
                .withName(VALID_NAME_COACHELLA)
                .build();
        EditConcertCommand expectedCommand = new EditConcertCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_COACHELLA;
        descriptor = new EditConcertDescriptorBuilder().withAddress(VALID_ADDRESS_COACHELLA).build();
        expectedCommand = new EditConcertCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_COACHECLLA;
        descriptor = new EditConcertDescriptorBuilder().withDate(VALID_DATE_COACHELLA).build();
        expectedCommand = new EditConcertCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddConcertCommandParserTest#parse_repeatedValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_CONCERT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COACHELLA + INVALID_NAME_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_COACHELLA;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_NAME));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA + DATE_DESC_COACHECLLA
                + NAME_DESC_COACHELLA + ADDRESS_DESC_COACHELLA + DATE_DESC_COACHECLLA;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + INVALID_ADDRESS_DESC + INVALID_DATE_DESC
                + INVALID_NAME_DESC + INVALID_DATE_DESC + INVALID_ADDRESS_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_NAME, PREFIX_DATE, PREFIX_ADDRESS));
    }
}
