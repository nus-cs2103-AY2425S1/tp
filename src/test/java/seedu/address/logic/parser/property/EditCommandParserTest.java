package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.ASKING_PRICE_DESC_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.ASKING_PRICE_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASKING_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LANDLORD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LANDLORD_NAME_DESC_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.PROPERTY_TYPE_DESC_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.PROPERTY_TYPE_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LANDLORD_NAME_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_ALAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASKING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.logging.*;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.property.EditCommand;
import seedu.address.logic.commands.property.EditCommand.EditPropertyDescriptor;
import seedu.address.model.property.Address;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Phone;
import seedu.address.testutil.property.EditPropertyDescriptorBuilder;

public class EditCommandParserTest {
    private static Logger logger = Logger.getLogger("Foo");
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private seedu.address.logic.parser.property.EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_LANDLORD_NAME_ALAN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + LANDLORD_NAME_DESC_ALAN, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + LANDLORD_NAME_DESC_ALAN, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
        + INVALID_LANDLORD_NAME_DESC, LandlordName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1"
        + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1"
        + INVALID_ASKING_PRICE_DESC, AskingPrice.MESSAGE_CONSTRAINTS); // invalid budget

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + ADDRESS_DESC_ALAN, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_LANDLORD_NAME_DESC + INVALID_ADDRESS_DESC + VALID_ASKING_PRICE_ALAN
                        + VALID_PHONE_ALAN, LandlordName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BRENDA + PROPERTY_TYPE_DESC_ALAN
                + ADDRESS_DESC_ALAN + ASKING_PRICE_DESC_ALAN + LANDLORD_NAME_DESC_ALAN;

        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
        .withLandlordName(VALID_LANDLORD_NAME_ALAN)
                .withPhone(VALID_PHONE_BRENDA)
                .withAddress(VALID_ADDRESS_ALAN)
                .withAskingPrice(VALID_ASKING_PRICE_ALAN)
                .withPropertyType(VALID_PROPERTY_TYPE_ALAN)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BRENDA + ADDRESS_DESC_ALAN;

        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder().withPhone(VALID_PHONE_BRENDA)
                .withAddress(VALID_ADDRESS_ALAN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // landLordName
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + LANDLORD_NAME_DESC_ALAN;
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withLandlordName(VALID_LANDLORD_NAME_ALAN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_ALAN;
        descriptor = new EditPropertyDescriptorBuilder().withPhone(VALID_PHONE_ALAN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_ALAN;
        descriptor = new EditPropertyDescriptorBuilder().withAddress(VALID_ADDRESS_ALAN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // asking price
        userInput = targetIndex.getOneBased() + ASKING_PRICE_DESC_ALAN;
        descriptor = new EditPropertyDescriptorBuilder().withAskingPrice(VALID_ASKING_PRICE_ALAN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // property type
        userInput = targetIndex.getOneBased() + PROPERTY_TYPE_DESC_ALAN;
        descriptor = new EditPropertyDescriptorBuilder().withPropertyType(VALID_PROPERTY_TYPE_ALAN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BRENDA;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BRENDA + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_ALAN + ASKING_PRICE_DESC_ALAN + ADDRESS_DESC_ALAN
                + PROPERTY_TYPE_DESC_ALAN + PHONE_DESC_ALAN + ASKING_PRICE_DESC_ALAN + ADDRESS_DESC_ALAN
                + PROPERTY_TYPE_DESC_ALAN + PHONE_DESC_BRENDA + ASKING_PRICE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + PROPERTY_TYPE_DESC_BRENDA;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ASKING_PRICE,
                        PREFIX_TYPE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ASKING_PRICE_DESC + INVALID_ADDRESS_DESC
                + INVALID_PHONE_DESC + INVALID_ASKING_PRICE_DESC + INVALID_ADDRESS_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ASKING_PRICE));
    }

}
