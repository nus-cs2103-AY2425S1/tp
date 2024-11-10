package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SuperFindCommand;
import seedu.address.model.person.CombinedContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SuperFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithNames() {
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(namePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n n/ \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithPhones() {
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("91234567", "995"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(phonePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " p/ 91234567 p/ 995", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " p/ \n 91234567 \n p/\t 995  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithEmails() {
        EmailContainsKeywordsPredicate emailPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("ryan@gmail.com", "tasha@gmail.com"));

        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(emailPredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " e/ ryan@gmail.com e/tasha@gmail.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n ryan@gmail.com \n e/\t tasha@gmail.com  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithTags() {
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList("PC2174ALecturer", "PC2032classmate"));

        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(tagPredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " t/PC2174ALecturer t/PC2032classmate", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/ \n PC2174ALecturer \n t/\t PC2032classmate  \t", expectedFindCommand);
    }

    @Test
    public void parse_combinedArgs_returnsAppropriateFindCommand() {
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("91234567"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(namePredicate, phonePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        assertParseSuccess(parser, " n/Alice p/91234567", expectedFindCommand);
    }

    @Test
    public void parse_caseInsensitiveKeywords_success() {
        // Sample persons
        Person alice = new PersonBuilder().withName("ALICE").build();
        Person bob = new PersonBuilder().withName("BOb").build();
        Person charlie = new PersonBuilder().withName("ChaRLie").build();
        List<Person> personList = Arrays.asList(alice, bob, charlie);

        // Setting up case-insensitive predicates
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob")); // Lowercase for input
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(namePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // Filter using the predicate created from parsing
        List<Person> filteredPersons = personList.stream()
                .filter(expectedPredicate)
                .collect(Collectors.toList());

        // Assertions to confirm the output is correct
        assertTrue(filteredPersons.contains(alice)); // Alice should be in the result
        assertTrue(filteredPersons.contains(bob)); // Bob should be in the result
        assertFalse(filteredPersons.contains(charlie)); // Charlie should not be in the result
        assertEquals(2, filteredPersons.size()); // Expected size of filtered persons to be 2
    }

    @Test
    public void parse_emptyPrefixBetweenValidones_validParse() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        Person charlie = new PersonBuilder().withName("Charlie").build();
        List<Person> personList = Arrays.asList(alice, bob, charlie);

        // Setting up the predicates
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "", "Bob"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(namePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // Simulating parse success
        assertParseSuccess(parser, " n/Alice n/ n/Bob", expectedFindCommand);

        // Now verify that the predicate matches only the expected persons
        List<Person> filteredPersons = personList.stream()
                .filter(expectedPredicate)
                .collect(Collectors.toList());

        // Check if the filtered persons match the expected persons
        assertTrue(filteredPersons.contains(alice));
        assertTrue(filteredPersons.contains(bob));
        assertTrue(filteredPersons.contains(charlie));
        System.out.println(filteredPersons.size());
        assertEquals(3, filteredPersons.size()); // expect all to be present
    }

    @Test
    public void parse_longInput_validParse() {
        EmailContainsKeywordsPredicate emailPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("first@example.com", "second@example.com"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(emailPredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        assertParseSuccess(parser, " e/"
                + String.join(" e/", "first@example.com", "second@example.com"), expectedFindCommand);
    }
}
