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
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
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

        // EP: same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // EP: same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // EP: different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // EP: null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // EP: different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonPredicate pred = preparePredicate(" ");
        System.out.println(pred);
        FilterCommand command = new FilterCommand(pred);
        expectedModel.updateFilteredPersonList(pred);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonPredicate predicate = preparePredicate("filter n/Alice n/Benson");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

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

        List<String> names = argMultimap.getAllValues(PREFIX_NAME).stream()
                .flatMap(name -> List.of(name.trim().split("\\s+")).stream()).toList();

        List<String> phones = argMultimap.getAllValues(PREFIX_PHONE).stream()
                .flatMap(phone -> List.of(phone.trim().split("\\s+")).stream()).toList();

        List<String> emails = argMultimap.getAllValues(PREFIX_EMAIL).stream()
                .flatMap(email -> List.of(email.trim().split("\\s+")).stream()).toList();

        List<String> addresses = argMultimap.getAllValues(PREFIX_ADDRESS).stream()
                .flatMap(addr -> List.of(addr.trim().split("\\s+")).stream()).toList();

        List<String> registerNumbers = argMultimap.getAllValues(PREFIX_REGISTER_NUMBER).stream()
                .flatMap(regNo -> List.of(regNo.trim().split("\\s+")).stream()).toList();

        List<String> sexes = argMultimap.getAllValues(PREFIX_SEX).stream()
                .flatMap(sex -> List.of(sex.trim().split("\\s+")).stream()).toList();

        List<String> classes = argMultimap.getAllValues(PREFIX_STUDENT_CLASS).stream()
                .flatMap(clss -> List.of(clss.trim().split("\\s+")).stream()).toList();

        List<String> ecNames = argMultimap.getAllValues(PREFIX_ECNAME).stream()
                .flatMap(ecName -> List.of(ecName.trim().split("\\s+")).stream()).toList();

        List<String> ecNumbers = argMultimap.getAllValues(PREFIX_ECNUMBER).stream()
                .flatMap(ecNum -> List.of(ecNum.trim().split("\\s+")).stream()).toList();

        List<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .flatMap(tag -> List.of(tag.trim().split("\\s+")).stream()).toList();

        return new PersonPredicate(names, phones, emails, addresses, registerNumbers, sexes,
                classes, ecNames, ecNumbers, tags);
    }
}
