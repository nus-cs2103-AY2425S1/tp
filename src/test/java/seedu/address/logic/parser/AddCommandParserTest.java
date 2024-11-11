package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CULTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LITERATURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailureEvent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessEvent;
import static seedu.address.testutil.TypicalEvents.ART_EXHIBIT;
import static seedu.address.testutil.TypicalEvents.BOOK_FAIR;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.AddEventCommand;
import seedu.address.logic.commands.personcommands.AddPersonCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.types.common.Address;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.Email;
import seedu.address.model.types.common.Name;
import seedu.address.model.types.common.Phone;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddPersonCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddPersonCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddPersonCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseEvent_allFieldsPresent_success() {
        // whitespace only preamble
        Event expectedEvent = new EventBuilder(ART_EXHIBIT).build();
        assertParseSuccessEvent(parser, PREAMBLE_WHITESPACE + NAME_DESC_ART_EXHIBIT + ADDRESS_DESC_ART_EXHIBIT
                + START_TIME_DESC_ART_EXHIBIT + TAG_DESC_CULTURE, new AddEventCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(BOOK_FAIR).build();
        assertParseSuccessEvent(parser, NAME_DESC_BOOK_FAIR + ADDRESS_DESC_BOOK_FAIR + START_TIME_DESC_BOOK_FAIR
                + TAG_DESC_CULTURE + TAG_DESC_LITERATURE, new AddEventCommand(expectedEventMultipleTags));
    }

    @Test
    public void parseEvent_repeatedNonTagValue_failure() {
        String validExpectedEventString = NAME_DESC_BOOK_FAIR + ADDRESS_DESC_BOOK_FAIR
                + START_TIME_DESC_BOOK_FAIR + TAG_DESC_LITERATURE;

        // multiple names
        assertParseFailureEvent(parser, NAME_DESC_BOOK_FAIR + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple address
        assertParseFailureEvent(parser, ADDRESS_DESC_BOOK_FAIR + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple start time
        assertParseFailureEvent(parser, START_TIME_DESC_BOOK_FAIR + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));

        // multiple fields repeated
        assertParseFailureEvent(parser,
                validExpectedEventString + NAME_DESC_BOOK_FAIR
                        + ADDRESS_DESC_BOOK_FAIR + START_TIME_DESC_BOOK_FAIR,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_START_TIME));

        // invalid value followed by valid value

        // invalid name
        assertParseFailureEvent(parser, INVALID_NAME_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid address
        assertParseFailureEvent(parser, INVALID_ADDRESS_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid start time
        assertParseFailureEvent(parser, INVALID_START_TIME_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));

        // valid value followed by invalid value

        // invalid name
        assertParseFailureEvent(parser, validExpectedEventString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid address
        assertParseFailureEvent(parser, validExpectedEventString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid start time
        assertParseFailureEvent(parser, validExpectedEventString + INVALID_START_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));
    }

    @Test
    public void parseEvent_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder(BOOK_FAIR).withTags().build();
        assertParseSuccessEvent(parser, NAME_DESC_BOOK_FAIR + ADDRESS_DESC_BOOK_FAIR
                        + START_TIME_DESC_BOOK_FAIR, new AddEventCommand(expectedEvent));
    }

    @Test
    public void parseEvent_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailureEvent(parser, VALID_ADDRESS_ART_EXHIBIT + ADDRESS_DESC_ART_EXHIBIT
                        + START_TIME_DESC_ART_EXHIBIT, expectedMessage);

        // missing address prefix
        assertParseFailureEvent(parser, NAME_DESC_ART_EXHIBIT + VALID_ADDRESS_ART_EXHIBIT
                + START_TIME_DESC_ART_EXHIBIT, expectedMessage);

        // missing start time prefix
        assertParseFailureEvent(parser, NAME_DESC_ART_EXHIBIT + ADDRESS_DESC_ART_EXHIBIT
                + VALID_START_TIME_ART_EXHIBIT, expectedMessage);

        // all prefixes missing
        assertParseFailureEvent(parser, VALID_NAME_ART_EXHIBIT + VALID_ADDRESS_ART_EXHIBIT
                        + VALID_START_TIME_ART_EXHIBIT, expectedMessage);
    }

    @Test
    public void parseEvent_invalidValue_failure() {
        // invalid name
        assertParseFailureEvent(parser, INVALID_NAME_DESC + ADDRESS_DESC_BOOK_FAIR + START_TIME_DESC_BOOK_FAIR
                + TAG_DESC_CULTURE + TAG_DESC_LITERATURE, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailureEvent(parser, NAME_DESC_BOOK_FAIR + INVALID_ADDRESS_DESC + START_TIME_DESC_BOOK_FAIR
                + TAG_DESC_CULTURE + TAG_DESC_LITERATURE, Address.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailureEvent(parser, NAME_DESC_BOOK_FAIR + ADDRESS_DESC_BOOK_FAIR + INVALID_START_TIME_DESC
                + TAG_DESC_CULTURE + TAG_DESC_LITERATURE, DateTime.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailureEvent(parser, NAME_DESC_BOOK_FAIR + ADDRESS_DESC_BOOK_FAIR + START_TIME_DESC_BOOK_FAIR
                + INVALID_TAG_DESC + TAG_DESC_LITERATURE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailureEvent(parser, INVALID_NAME_DESC + ADDRESS_DESC_BOOK_FAIR + INVALID_START_TIME_DESC
                + TAG_DESC_CULTURE + TAG_DESC_LITERATURE, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailureEvent(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOOK_FAIR + ADDRESS_DESC_BOOK_FAIR
                + START_TIME_DESC_BOOK_FAIR + TAG_DESC_CULTURE + TAG_DESC_LITERATURE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
