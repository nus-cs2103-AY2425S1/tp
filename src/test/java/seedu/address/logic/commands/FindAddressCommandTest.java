package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindAddressCommand}.
 */
public class FindAddressCommandTest {
    private Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClientHub(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate("first");
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate("second");

        FindAddressCommand findFirstAddressCommand = new FindAddressCommand(firstPredicate);
        FindAddressCommand findSecondAddressCommand = new FindAddressCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommand));

        // same values -> returns true
        FindAddressCommand findFirstAddressCommandCopy = new FindAddressCommand(firstPredicate);
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommandCopy));

        // different types -> returns false
        assertFalse(findFirstAddressCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstAddressCommand.equals(null));

        // different address -> returns false
        assertFalse(findFirstAddressCommand.equals(findSecondAddressCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate(" ");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getDisplayPersons());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("th street");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, GEORGE), model.getDisplayPersons());
    }

    @Test
    public void toStringMethod() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate("Tampines");
        FindAddressCommand findAddressCommand = new FindAddressCommand(predicate);
        String expected = FindAddressCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findAddressCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(userInput);
    }
}

