package seedu.address.logic.commands;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonPredicate firstPredicate =
                new PersonPredicate(Collections.singletonList("first"), emptyList(), emptyList(), emptyList(),
                        emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        PersonPredicate secondPredicate =
                new PersonPredicate(Collections.singletonList("second"), emptyList(), emptyList(), emptyList(),
                        emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonPredicate predicate = preparePredicate(" ");
        System.out.println(predicate);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(emptyList(), model.getFilteredPersonList());
    }

    /*@Test - next iteration
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonPredicate predicate = preparePredicate("n/Fiona n/Carl");
        System.out.println("Parsed names: " + predicate);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        System.out.println("Filtered person list before command: " + model.getFilteredPersonList());
        System.out.println("Test data: " + model.getFilteredPersonList());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, CARL), model.getFilteredPersonList());
    }*/

    @Test
    public void toStringMethod() {
        PersonPredicate predicate = new PersonPredicate(Arrays.asList("keyword"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PersonPredicate}.
     */
    private PersonPredicate preparePredicate(String userInput) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_REGISTER_NUMBER, PREFIX_SEX,
                PREFIX_STUDENT_CLASS, PREFIX_ECNAME, PREFIX_ECNUMBER, PREFIX_TAG);

        List<String> names = argMultimap.getAllValues(PREFIX_NAME).stream().map(String::trim).toList();
        List<String> phones = argMultimap.getAllValues(PREFIX_PHONE).stream().map(String::trim).toList();
        List<String> emails = argMultimap.getAllValues(PREFIX_EMAIL).stream().map(String::trim).toList();
        List<String> addresses = argMultimap.getAllValues(PREFIX_ADDRESS).stream().map(String::trim).toList();
        List<String> registerNumbers = argMultimap.getAllValues(PREFIX_REGISTER_NUMBER).stream()
                .map(String::trim).toList();
        List<String> sexes = argMultimap.getAllValues(PREFIX_SEX).stream().map(String::trim).toList();
        List<String> classes = argMultimap.getAllValues(PREFIX_STUDENT_CLASS).stream().map(String::trim).toList();
        List<String> ecNames = argMultimap.getAllValues(PREFIX_ECNAME).stream().map(String::trim).toList();
        List<String> ecNumbers = argMultimap.getAllValues(PREFIX_ECNUMBER).stream().map(String::trim).toList();
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream().map(String::trim).toList();

        return new PersonPredicate(names, phones, emails, addresses, registerNumbers, sexes,
                classes, ecNames, ecNumbers, tags);
    }
}
