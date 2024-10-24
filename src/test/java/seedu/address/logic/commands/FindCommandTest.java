package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.predicate.FieldContainsKeywordsPredicate;
import seedu.address.model.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.predicate.TagContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final FieldContainsKeywordsPredicate<Person> firstNamePredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("first"), Person::getFullName, true);
    private final FieldContainsKeywordsPredicate<Person> secondNamePredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("second"), Person::getFullName, true);
    private final FieldContainsKeywordsPredicate<Person> thirdNamePredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("Second"), Person::getFullName, true);
    private final FieldContainsKeywordsPredicate<Person> thirdAddressPredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("second"), Person::getAddressValue, true);
    private final TagContainsKeywordPredicate firstTagPredicate = new TagContainsKeywordPredicate("friends");
    private final TagContainsKeywordPredicate secondTagPredicate = new TagContainsKeywordPredicate("friend");

    @Test
    public void equals() {

        FindCommand findFirstCommand = new FindCommand(List.of(firstNamePredicate));
        FindCommand findSecondCommand = new FindCommand(List.of(secondNamePredicate));
        FindCommand findCaseInsensitiveCommand = new FindCommand(List.of(thirdNamePredicate));
        FindCommand findThirdCommand = new FindCommand(List.of(firstNamePredicate, thirdAddressPredicate));
        FindCommand findForthCommand = new FindCommand(List.of(firstNamePredicate, thirdAddressPredicate));
        FindCommand findFifthCommand = new FindCommand(List.of(firstTagPredicate));
        FindCommand findSixthCommand = new FindCommand(List.of(secondTagPredicate));
        FindCommand findSeventhCommand = new FindCommand(List.of(secondTagPredicate));
        FindCommand emptyFindCommand = new FindCommand(Collections.emptyList());

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(List.of(firstNamePredicate));
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);

        // same findCommand predicates -> returns true
        assertEquals(findThirdCommand, findForthCommand);

        // different predicate numbers
        assertNotEquals(findFirstCommand, findSeventhCommand);

        // different tag -> returns false
        assertNotEquals(findFifthCommand, findSixthCommand);

        // same tag -> return true
        assertEquals(findSixthCommand, findSeventhCommand);

        // Empty predicate list vs non-empty predicate list
        assertNotEquals(emptyFindCommand, findFirstCommand);

        // predicate with different case -> return true
        assertEquals(findSecondCommand, findCaseInsensitiveCommand);
    }

    @Test
    public void equals_differentNumberOfPredicates_returnsFalse() {
        // One predicate vs two predicates
        FindCommand findFirstCommand = new FindCommand(List.of(firstNamePredicate));
        FindCommand findSecondCommand = new FindCommand(List.of(firstNamePredicate, thirdAddressPredicate));

        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void equals_differentFieldsDifferentValues_returnsFalse() {
        // First command searches by name, second command searches by address
        FindCommand findFirstCommand = new FindCommand(List.of(firstNamePredicate));
        FindCommand findByAddressCommand = new FindCommand(List.of(thirdAddressPredicate));

        assertNotEquals(findFirstCommand, findByAddressCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(List.of(firstNamePredicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(List.of(firstNamePredicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(List.of(firstNamePredicate));
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
