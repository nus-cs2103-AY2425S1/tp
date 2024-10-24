package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sex;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                + BIRTHDATE_DESC_BOB, new AddCommand(expectedPerson));


        // multiple health services - all accepted
        Person expectedPersonMultipleHealthServices = new PersonBuilder(BOB).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                        + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPersonMultipleHealthServices));
    }

    @Test
    public void parse_repeatedNonHealthServiceValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                + BIRTHDATE_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple NRICs
        assertParseFailure(parser, NRIC_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple sexes
        assertParseFailure(parser, SEX_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // multiple birthdates
        assertParseFailure(parser, BIRTHDATE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + NAME_DESC_AMY + NRIC_DESC_AMY + SEX_DESC_AMY + BIRTHDATE_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX, PREFIX_BIRTHDATE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid NRIC
        assertParseFailure(parser, INVALID_NRIC_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid Sex
        assertParseFailure(parser, INVALID_SEX_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // invalid Birthdate
        assertParseFailure(parser, INVALID_BIRTHDATE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid NRIC
        assertParseFailure(parser, validExpectedPersonString + INVALID_NRIC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid Sex
        assertParseFailure(parser, validExpectedPersonString + INVALID_SEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // invalid Birthdate
        assertParseFailure(parser, validExpectedPersonString + INVALID_BIRTHDATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB,
                expectedMessage);

        // missing NRIC prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_NRIC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB,
                expectedMessage);

        // missing SEX prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + VALID_SEX_BOB + BIRTHDATE_DESC_BOB,
                expectedMessage);

        // missing Birthdate prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + VALID_BIRTHDATE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_NRIC_BOB + VALID_SEX_BOB + VALID_BIRTHDATE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid NRIC
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + SEX_DESC_BOB + BIRTHDATE_DESC_BOB,
                Nric.MESSAGE_CONSTRAINTS);

        // invalid Sex
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + INVALID_SEX_DESC + BIRTHDATE_DESC_BOB,
                Sex.MESSAGE_CONSTRAINTS);

        // invalid Birthdate
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + INVALID_BIRTHDATE_DESC,
                Birthdate.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + SEX_DESC_BOB + INVALID_BIRTHDATE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                + BIRTHDATE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
