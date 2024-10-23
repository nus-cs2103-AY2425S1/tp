package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    private Model getDefaultModel() {
        return new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    }

    @Test
    public void parse_missingParts_failure() {
        // invalid name specified
        assertParseFailure(parser, "$$", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_NAME_AMY, EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid name
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid name
        assertParseFailure(parser, "$$" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // invalid phone
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        // invalid address
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Name targetName = getDefaultModel().getPersonList().get(0).getName();
        String userInput = targetName.fullName + PHONE_DESC_AMY + TAG_DESC_HUSBAND
                + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptor(
                Optional.of(new Name(VALID_NAME_AMY)),
                Optional.of(new Phone(VALID_PHONE_AMY)),
                Optional.of(new Address(VALID_ADDRESS_AMY)),
                Optional.of(Set.of(new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_FRIEND))));

        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Name targetName = getDefaultModel().getPersonList().get(0).getName();
        String userInput = targetName.fullName + PHONE_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptor(
                Optional.empty(),
                Optional.of(new Phone(VALID_PHONE_BOB)),
                Optional.empty(),
                Optional.empty());

        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Name targetName = getDefaultModel().getPersonList().get(0).getName();
        String userInput = targetName.fullName + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptor(
                Optional.of(new Name(VALID_NAME_AMY)),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetName.fullName + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptor(
                Optional.empty(),
                Optional.of(new Phone(VALID_PHONE_AMY)),
                Optional.empty(),
                Optional.empty());
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetName.fullName + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptor(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new Address(VALID_ADDRESS_AMY)),
                Optional.empty());
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetName.fullName + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptor(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(Set.of(new Tag(VALID_TAG_FRIEND))));
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Name targetName = getDefaultModel().getPersonList().get(0).getName();
        String userInput = targetName.fullName + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetName.fullName + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetName.fullName + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetName.fullName + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Name targetName = getDefaultModel().getPersonList().get(0).getName();
        String userInput = targetName.fullName + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptor(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(Set.of()));
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
