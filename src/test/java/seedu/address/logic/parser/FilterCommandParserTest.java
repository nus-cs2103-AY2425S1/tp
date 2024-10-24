package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasFeaturePredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    private PersonHasFeaturePredicate highTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null, null, null);
    private PersonHasFeaturePredicate lowTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_LOW_RISK), null, null, null);

    private PersonHasFeaturePredicate mediumTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_MEDIUM_RISK), null, null, null);

    private PersonHasFeaturePredicate phoneOnlyPredicate =
          new PersonHasFeaturePredicate(null, new Phone(ALICE.getPhone().value), null, null);

    private PersonHasFeaturePredicate phoneAndTagPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK),
                  new Phone(ALICE.getPhone().value), null, null);

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        //non empty preamble
        FilterCommand expectedFilterCommand = new FilterCommand(phoneOnlyPredicate);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " p/ " + ALICE.getPhone().value,
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));;
        //invalid tag
        assertParseFailure(parser, INVALID_TAG_DESC,
              String.format(Tag.MESSAGE_CONSTRAINTS, FilterCommand.MESSAGE_USAGE));
        //invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC,
              String.format(Phone.MESSAGE_CONSTRAINTS, FilterCommand.MESSAGE_USAGE));

        //no features provided
        assertParseFailure(parser, "",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        //multiple tags provided
        assertParseFailure(parser, VALID_TAG_HIGH_RISK + VALID_TAG_MEDIUM_RISK,
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        //multiple phone numbers provided
        assertParseFailure(parser, PHONE_DESC_AMY + PHONE_DESC_BOB,
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));


    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        //phone number only
        FilterCommand expectedFilterCommand = new FilterCommand(phoneOnlyPredicate);
        assertParseSuccess(parser, " p/ " + ALICE.getPhone().value, expectedFilterCommand);

        //parse with preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " p/ " + ALICE.getPhone().value, expectedFilterCommand);
        //tag only
        expectedFilterCommand = new FilterCommand(highTagOnlyPredicate);
        assertParseSuccess(parser, " t/ High Risk", expectedFilterCommand);

        //phone number and tag
        expectedFilterCommand =
              new FilterCommand(phoneAndTagPredicate);
        assertParseSuccess(parser, " t/ High Risk p/" + ALICE.getPhone().value, expectedFilterCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/ High Risk \n \t p/" + ALICE.getPhone().value,
              expectedFilterCommand);



    }
    @Test
    public void parse_noFieldsPresent_failure() {
        // No tag, no phone, no email, no address
        String userInput = " ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_tagOnly_success() throws Exception {
        // Only tag provided
        String userInput = " t/" + VALID_TAG_HIGH_RISK;
        assertDoesNotThrow(() -> parser.parse(userInput));
    }

    @Test
    public void parse_phoneOnly_success() throws Exception {
        // Only phone provided
        String userInput = " p/" + ALICE.getPhone();
        assertDoesNotThrow(() -> parser.parse(userInput));
    }

    @Test
    public void parse_emailOnly_success() throws Exception {
        // Only email provided
        String userInput = " e/" + ALICE.getEmail();
        assertDoesNotThrow(() -> parser.parse(userInput));
    }

    @Test
    public void parse_addressOnly_success() throws Exception {
        // Only address provided
        String userInput = " a/" + ALICE.getAddress();
        assertDoesNotThrow(() -> parser.parse(userInput));
    }

    @Test
    public void parse_tagAndPhone_success() throws Exception {
        // Tag and phone provided, but no email or address
        String userInput = " t/" + VALID_TAG_HIGH_RISK + " p/" + ALICE.getPhone();
        assertDoesNotThrow(() -> parser.parse(userInput));
    }

    @Test
    public void parse_emailAndAddress_success() throws Exception {
        // Both email and address provided
        String userInput = " e/" + ALICE.getEmail() + " a/" + ALICE.getAddress();
        assertDoesNotThrow(() -> parser.parse(userInput));
    }

    @Test
    public void parseinvalidInputnoValidPrefixesfailure() {
        // No valid prefixes at all
        String invalidUserInput = " random invalid input";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_validEmail_success() throws Exception {
        String userInput = " e/" + ALICE.getEmail();
        FilterCommand command = parser.parse(userInput);

        // Assertions
        PersonHasFeaturePredicate expectedPredicate = new PersonHasFeaturePredicate(null,
                null, ALICE.getEmail(), null);
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parse_validAddress_success() throws Exception {
        String userInput = " a/" + ALICE.getAddress();
        FilterCommand command = parser.parse(userInput);

        // Assertions
        PersonHasFeaturePredicate expectedPredicate =
                new PersonHasFeaturePredicate(null, null, null, ALICE.getAddress());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parse_validEmailAndAddress_success() throws Exception {
        String userInput = " e/" + ALICE.getEmail() + " a/" + ALICE.getAddress();
        FilterCommand command = parser.parse(userInput);

        // Assertions
        PersonHasFeaturePredicate expectedPredicate =
                new PersonHasFeaturePredicate(null, null, ALICE.getEmail(), ALICE.getAddress());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = " t/" + ALICE.getTag() + " p/" + ALICE.getPhone()
                + " e/" + ALICE.getEmail() + " a/" + ALICE.getAddress();
        FilterCommand command = parser.parse(userInput);

        // Assertions
        PersonHasFeaturePredicate expectedPredicate = new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK),
                ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parse_invalidEmail_failure() {
        String invalidEmailInput = " e/invalidEmail";
        assertThrows(ParseException.class, () -> parser.parse(invalidEmailInput));
    }

    @Test
    public void parse_invalidAddress_failure() {
        String invalidAddressInput = " a/"; // Assuming an empty address is invalid
        assertThrows(ParseException.class, () -> parser.parse(invalidAddressInput));
    }

    @Test
    public void parse_multipleTags_throwsParseException() {

        // Simulate user input with multiple tags
        String userInput = " t/HighRisk t/LowRisk";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_multiplePhones_throwsParseException() {
        // Simulate user input with multiple phone numbers
        String userInput = " p/12345678 p/87654321"; // Multiple phone prefixes

        assertThrows(ParseException.class, () -> parser.parse(userInput)); // Ensure the parser throws the exception

    }

    @Test
    public void parse_multipleEmails_throwsParseException() {
        FilterCommandParser parser = new FilterCommandParser();

        // Simulate user input with multiple emails
        String userInput = " e/test1@example.com e/test2@example.com";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_multipleAddresses_throwsParseException() {
        FilterCommandParser parser = new FilterCommandParser();

        // Simulate user input with multiple addresses
        String userInput = " a/123 Main St a/456 Elm St";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseonlyTagPresentnoEmailNoAddresssuccess() throws Exception {
        // Simulate user input with tag, but no email or address
        String userInput = " t/Low Risk";

        FilterCommand command = new FilterCommandParser().parse(userInput);

        // Verify that the command was parsed correctly without throwing an exception
        assertNotNull(command); // Command should be created successfully
    }

    @Test
    public void parsenoEmailNoAddressnoOtherFiltersthrowsParseException() {
        // Simulate user input with no email, no address, and no other filters
        String userInput = "";

        // Verify that the ParseException is thrown due to no valid input provided
        assertThrows(ParseException.class, () -> new FilterCommandParser().parse(userInput));
    }

}
