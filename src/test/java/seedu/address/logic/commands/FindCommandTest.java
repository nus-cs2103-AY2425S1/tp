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
import seedu.address.model.person.InFilteredListPredicate;
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

        List<Predicate<Person>> firstNamePredicates = Arrays.asList(firstNamePredicate);
        List<Predicate<Person>> secondNamePredicates = Arrays.asList(secondNamePredicate);
        List<Predicate<Person>> firstModuleRolePredicates = Arrays.asList(firstModuleRolePredicate);
        List<Predicate<Person>> secondModuleRolePredicates = Arrays.asList(secondModuleRolePredicate);
        List<Predicate<Person>> firstNameAndModuleRolePredicates =
                Arrays.asList(firstNamePredicate, firstModuleRolePredicate);
        List<Predicate<Person>> secondNameAndModuleRolePredicates =
                Arrays.asList(secondNamePredicate, secondModuleRolePredicate);


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
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0, "() AND ()");
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
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3, "(Kurz OR Elle OR Kunz)");
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
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6, "(CS1101S-Student)");
        ModuleRoleContainsKeywordsPredicate moduleRolePredicate = prepareModuleRolePredicate("CS1101S");
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(moduleRolePredicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(moduleRolePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndModuleRoleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2,
                "(Kurz OR Elle) AND (CS1101S-Student)");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle");
        ModuleRoleContainsKeywordsPredicate moduleRolePredicate = prepareModuleRolePredicate("CS1101S");
        List<Predicate<Person>> predicates = Arrays.asList(namePredicate, moduleRolePredicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(namePredicate.and(moduleRolePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void executeTwice_nameAndModuleRoleKeywordsChained_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2,
                "(Kurz OR Elle) AND (CS1101S-Student), among the previously displayed results");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle");
        ModuleRoleContainsKeywordsPredicate moduleRolePredicate = prepareModuleRolePredicate("CS1101S");
        InFilteredListPredicate inFilteredListPredicate = new InFilteredListPredicate(
                new ArrayList<>(model.getFilteredPersonList()));

        FindCommand command = new FindCommand(List.of(namePredicate, moduleRolePredicate), true);
        expectedModel.updateFilteredPersonList(namePredicate.and(moduleRolePredicate).and(inFilteredListPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE), model.getFilteredPersonList());

        // find again
        String expectedMessage2 = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1,
                "(e), among the previously displayed results");
        NameContainsKeywordsPredicate namePredicate2 = prepareNamePredicate("e");
        InFilteredListPredicate inFilteredListPredicate2 = new InFilteredListPredicate(
                new ArrayList<>(model.getFilteredPersonList()));
        FindCommand command2 = new FindCommand(List.of(namePredicate2), true);
        expectedModel.updateFilteredPersonList(namePredicate2.and(inFilteredListPredicate2));
        assertCommandSuccess(command2, model, expectedMessage2, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());
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
