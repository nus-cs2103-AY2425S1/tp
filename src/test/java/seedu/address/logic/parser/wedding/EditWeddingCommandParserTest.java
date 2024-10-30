package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEDDING_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_WEDDING_PERSON_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_WEDDING_PERSON_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.wedding.EditWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.EditWeddingDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class EditWeddingCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingCommand.MESSAGE_USAGE);

    private EditWeddingCommandParser parser = new EditWeddingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_WEDDING_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditWeddingCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + WEDDING_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + WEDDING_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_WEDDING_DESC, WeddingName.MESSAGE_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY + WEDDING_DESC_AMY;

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_AMY)
                .withAddress(VALID_ADDRESS_AMY).build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + WEDDING_DESC_AMY;

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_AMY).build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()
                + WEDDING_DESC_AMY
                + ADDRESS_DESC_AMY;

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder()
                .withName(VALID_WEDDING_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nonEditableFieldSpecified_failure() {
        String userInput = "1 Z/";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyIndex_failure() {
        assertParseFailure(parser, " " + WEDDING_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_partner1Specified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()
                + WEDDING_DESC_AMY
                + " " + PREFIX_EDIT_WEDDING_PERSON_1 + "1"; // valid partner 1 index

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder()
                .withName(VALID_WEDDING_AMY)
                .withPartner1(new PersonBuilder().withName("Bob").build()).build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_partner2Specified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()
                + WEDDING_DESC_AMY
                + " " + PREFIX_EDIT_WEDDING_PERSON_2 + "2"; // valid partner 2 index

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder()
                .withName(VALID_WEDDING_AMY)
                .withPartner1(new PersonBuilder().withName("Bob").build()).build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidPartner1Index_failure() {
        String userInput = "1"
                + WEDDING_DESC_AMY
                + " " + PREFIX_EDIT_WEDDING_PERSON_1 + "-1"; // invalid partner 1 index

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPartner2Index_failure() {
        String userInput = "1"
                + WEDDING_DESC_AMY
                + " " + PREFIX_EDIT_WEDDING_PERSON_2 + "0"; // invalid partner 2 index

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingPartner1_failure() {
        String userInput = "1"
                + WEDDING_DESC_AMY
                + " " + PREFIX_EDIT_WEDDING_PERSON_1; // no index specified

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingPartner2_failure() {
        String userInput = "1"
                + WEDDING_DESC_AMY
                + " " + PREFIX_EDIT_WEDDING_PERSON_2; // no index specified

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
