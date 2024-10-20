package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws ParseException {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        ModuleRoleContainsKeywordsPredicate firstModuleRolePredicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Collections.singletonList("CS1010")));
        ModuleRoleContainsKeywordsPredicate secondModuleRolePredicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Collections.singletonList("CS2103T-prof")));

        List<Predicate<Person>> firstNamePredicates = new ArrayList<>();
        firstNamePredicates.add(firstNamePredicate);

        List<Predicate<Person>> secondNamePredicates = new ArrayList<>();
        secondNamePredicates.add(secondNamePredicate);

        List<Predicate<Person>> firstModuleRolePredicates = new ArrayList<>();
        firstModuleRolePredicates.add(firstModuleRolePredicate);

        List<Predicate<Person>> secondModuleRolePredicates = new ArrayList<>();
        secondModuleRolePredicates.add(secondModuleRolePredicate);

        List<Predicate<Person>> firstNameAndModuleRolePredicates = new ArrayList<>();
        firstNameAndModuleRolePredicates.add(firstNamePredicate);
        firstNameAndModuleRolePredicates.add(firstModuleRolePredicate);

        List<Predicate<Person>> secondNameAndModuleRolePredicates = new ArrayList<>();
        secondNameAndModuleRolePredicates.add(secondNamePredicate);
        secondNameAndModuleRolePredicates.add(secondModuleRolePredicate);

        FindCommand findFirstNameCommand = new FindCommand(firstNamePredicates);
        FindCommand findSecondNameCommand = new FindCommand(secondNamePredicates);

        FindCommand findFirstModuleRoleCommand = new FindCommand(firstModuleRolePredicates);
        FindCommand findSecondModuleRoleCommand = new FindCommand(secondModuleRolePredicates);

        // Create FindCommand with both name and module predicates
        FindCommand findFirstNameAndModuleRoleCommand = new FindCommand(firstNameAndModuleRolePredicates);
        FindCommand findSecondNameAndModuleRoleCommand = new FindCommand(secondNameAndModuleRolePredicates);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));
        assertTrue(findFirstModuleRoleCommand.equals(findFirstModuleRoleCommand));
        assertTrue(findFirstNameAndModuleRoleCommand.equals(findFirstNameAndModuleRoleCommand));

        // same values -> returns true
        FindCommand findFirstNameCommandCopy = new FindCommand(new ArrayList<>(firstNamePredicates));
        FindCommand findFirstModuleRoleCommandCopy = new FindCommand(new ArrayList<>(firstModuleRolePredicates));
        assertTrue(findFirstNameCommand.equals(findFirstNameCommandCopy));
        assertTrue(findFirstModuleRoleCommand.equals(findFirstModuleRoleCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));
        assertFalse(findFirstModuleRoleCommand.equals(1));
        assertFalse(findFirstNameAndModuleRoleCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));
        assertFalse(findFirstModuleRoleCommand.equals(null));
        assertFalse(findFirstNameAndModuleRoleCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));

        // different module-role -> returns false
        assertFalse(findFirstModuleRoleCommand.equals(findSecondModuleRoleCommand));

        // different name and module-role predicates -> returns false
        assertFalse(findFirstNameAndModuleRoleCommand.equals(findSecondNameAndModuleRoleCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0, "");
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(prepareNamePredicate(" "));
        predicates.add(prepareModuleRolePredicate(" "));

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(person -> false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3, "\"Kurz\", \"Elle\", \"Kunz\"");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(namePredicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleModuleRoleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6, "\"CS1101S-Student\"");
        ModuleRoleContainsKeywordsPredicate moduleRolePredicate = prepareModuleRolePredicate("CS1101S");
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(moduleRolePredicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(moduleRolePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() throws ParseException {
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(new NameContainsKeywordsPredicate(List.of("keyword")));
        predicates.add(new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(List.of("CS2103T"))));

        FindCommand findCommand = new FindCommand(predicates);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicates + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ModuleRoleContainsKeywordsPredicate prepareModuleRolePredicate(String userInput) throws ParseException {
        return new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                Arrays.asList(userInput.split("\\s+"))));
    }
}
