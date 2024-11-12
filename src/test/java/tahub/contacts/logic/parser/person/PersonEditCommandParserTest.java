package tahub.contacts.logic.parser.person;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.MATRICULATION_NUMBER_DESC_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static tahub.contacts.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_MATRICULATION_NUMBER_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_PHONE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_TAG;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.core.index.Index;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.person.PersonEditCommand;
import tahub.contacts.logic.commands.person.PersonEditCommand.EditPersonDescriptor;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.tag.Tag;
import tahub.contacts.testutil.EditPersonDescriptorBuilder;
import tahub.contacts.testutil.TypicalPersons;

public class PersonEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PersonEditCommand.MESSAGE_USAGE);

    private PersonEditCommandParser parser = new PersonEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no student matriculation number specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " " + PREFIX_MATRICULATION_NUMBER + VALID_MATRICULATION_NUMBER_BOB,
                PersonEditCommand.MESSAGE_NOT_EDITED);

        // no student matriculation number and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " " + VALID_MATRICULATION_NUMBER_BOB
                + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " " + VALID_MATRICULATION_NUMBER_BOB
                + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB
                        + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB
                        + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + TAG_DESC_FRIEND
                + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + TAG_EMPTY
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " " + MATRICULATION_NUMBER_DESC_BOB + INVALID_NAME_DESC
                        + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        Person targetPerson = TypicalPersons.getTypicalPersons().get(targetIndex.getZeroBased());
        String userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        PersonEditCommand expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Person targetPerson = TypicalPersons.getTypicalPersons().get(targetIndex.getZeroBased());
        String userInput = " " + PREFIX_MATRICULATION_NUMBER + targetPerson.getMatricNumber()
                + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        PersonEditCommand expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        Person targetPerson = TypicalPersons.getTypicalPersons().get(targetIndex.getZeroBased());
        String userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        PersonEditCommand expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        Person targetPerson = TypicalPersons.getTypicalPersons().get(targetIndex.getZeroBased());
        String userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        Person targetPerson = TypicalPersons.getTypicalPersons().get(targetIndex.getZeroBased());
        String userInput = " " + PREFIX_MATRICULATION_NUMBER
                + targetPerson.getMatricNumber() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        PersonEditCommand expectedCommand = new PersonEditCommand(targetPerson.getMatricNumber(), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
