package seedu.address.logic.parser;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_INCOMPLETE_COMMAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleNames() {
        // Test case with "n/Alice n/Bob" (multiple prefixes)
        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice", "Bob"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter n/Alice n/Bob", expectedCommand);
        assertParseSuccess(parser, "filter n/Alice Bob", expectedCommand);

        // EP: multiple whitespaces between keywords
        assertParseSuccess(parser, "filter \n n/Alice \n \t n/Bob  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultiplePhones() {
        // Test case with multiple phone values "p/12345678 p/87654321"
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), List.of("12345678", "87654321"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter p/12345678 p/87654321", expectedCommand);
        assertParseSuccess(parser, "filter p/12345678 87654321", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n p/12345678 \n \t p/87654321  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleEmails() {
        // Test case with multiple email values "e/alice@example.com e/bob@example.com"
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), emptyList(), List.of("alice@example.com", "bob@example.com"),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter e/alice@example.com e/bob@example.com", expectedCommand);
        assertParseSuccess(parser, "filter e/alice@example.com bob@example.com", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n e/alice@example.com \n \t e/bob@example.com  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleRegisterNumbers() {
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), emptyList(), emptyList(),
                emptyList(), List.of("12", "36"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter r/12 r/36", expectedCommand);
        assertParseSuccess(parser, "filter r/12 36", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n r/12 \n \t r/36  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleSexes() {
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), List.of("M", "F"), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter s/M s/F", expectedCommand);
        assertParseSuccess(parser, "filter s/M F", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n s/M \n \t s/F  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleClasses() {
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), List.of("1A", "1B"), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter c/1A c/1B", expectedCommand);
        assertParseSuccess(parser, "filter c/1A 1B", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n c/1A \n \t c/1B  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleEcNames() {
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), List.of("Alice", "Bob"), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter en/Alice en/Bob", expectedCommand);
        assertParseSuccess(parser, "filter en/Alice Bob", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n en/Alice \n \t en/Bob  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultipleEcNumbers() {
        PersonPredicate expectedPredicate = new PersonPredicate(
                emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), List.of("91112222", "92223333"),
                emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter ep/91112222 ep/92223333", expectedCommand);
        assertParseSuccess(parser, "filter ep/91112222 92223333", expectedCommand);

        // EP: Test with multiple whitespaces
        assertParseSuccess(parser, "filter \n ep/91112222 \n \t ep/92223333  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultiplePrefixes() {
        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice"), List.of("99999999"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter n/Alice p/99999999", expectedCommand);

        // EP: multiple whitespaces between keywords
        assertParseSuccess(parser, "filter \n n/Alice \n \t p/99999999  \t", expectedCommand);
    }

    @Test
    void parse_invalidArgs_returnsFilterCommandMultiplePrefixes() throws ParseException {
        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice"), List.of("98898989"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        // Parse the command
        FilterCommand actualCommand = parser.parse("filter n/Alice p/98898989");

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommandMultiplePredicates() throws ParseException {
        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice", "Bernice"), List.of("99999999", "99272758"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter n/Alice Bernice p/99999999 99272758", expectedCommand);
        assertParseSuccess(parser, "filter n/Alice n/Bernice p/99999999 p/99272758", expectedCommand);
    }

    @Test
    void parse_invalidArgs_namePrefixFilterCommand() {
        // EP: empty name predicate
        assertParseFailure(parser, "filter n/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_phonePrefixFilterCommand() {
        // EP: empty phone predicate
        assertParseFailure(parser, "filter p/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_emailPrefixFilterCommand() {
        // EP: empty email predicate
        assertParseFailure(parser, "filter e/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_addressPrefixFilterCommand() {
        // EP: empty address predicate
        assertParseFailure(parser, "filter a/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_regNumPrefixFilterCommand() {
        // EP: empty register number predicate
        assertParseFailure(parser, "filter r/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_sexPrefixFilterCommand() {
        // EP: empty sex predicate
        assertParseFailure(parser, "filter s/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_classPrefixFilterCommand() {
        // EP: empty class predicate
        assertParseFailure(parser, "filter c/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_ecNamePrefixFilterCommand() {
        // EP: empty ecName predicate
        assertParseFailure(parser, "filter en/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_ecPhonePrefixFilterCommand() {
        // EP: empty ecPhone predicate
        assertParseFailure(parser, "filter ep/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_tagPrefixFilterCommand() {
        // EP: empty ecTag predicate
        assertParseFailure(parser, "filter t/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_spacesPrefixFilterCommand() {
        // EP: multiple empty predicates
        String emptyPreds = "filter n/ p/ e/ a/ r/ s/ c/ en/ ep/ t/";
        assertParseFailure(parser, emptyPreds, MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_allEmptyPredicatesFilterCommand() {
        String exceptionString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        // EP: Empty predicates
        assertParseFailure(parser, "filter", exceptionString);
        assertParseFailure(parser, "filter  ", exceptionString);
        assertParseFailure(parser, "filter n", exceptionString);
    }

    @Test
    void parse_invalidNameFormat_throwsParseException() {
        // Test case with an invalid name format
        assertParseFailure(parser, "filter n/!@#$", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidPhoneFormat_throwsParseException() {
        // Test case with an invalid phone format
        assertParseFailure(parser, "filter p/abcd1234", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidEmailFormat_throwsParseException() {
        // Test case with an invalid email format
        assertParseFailure(parser, "filter e/invalid_email", Email.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidRegisterNumberFormat_throwsParseException() {
        // Test case with an invalid register number format
        assertParseFailure(parser, "filter r/12345678abc", RegisterNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidSexFormat_throwsParseException() {
        // Test case with an invalid sex format
        assertParseFailure(parser, "filter s/unknown", Sex.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidStudentClassFormat_throwsParseException() {
        // Test case with an invalid class format
        assertParseFailure(parser, "filter c/invalidclass!", StudentClass.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidEcNameFormat_throwsParseException() {
        // Test case with an invalid emergency contact name format
        assertParseFailure(parser, "filter en/@@#$", EcName.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidEcNumberFormat_throwsParseException() {
        // Test case with an invalid emergency contact number format
        assertParseFailure(parser, "filter ep/abc1234", EcNumber.MESSAGE_CONSTRAINTS);
    }

}
