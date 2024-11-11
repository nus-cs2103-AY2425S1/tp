package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PETS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pet.PetNameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalPets;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPetsCommand}.
 */
public class FindPetCommandTest {
    private Model model = new ModelManager(TypicalPets.getTypicalPawPatrol(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalPets.getTypicalPawPatrol(), new UserPrefs());

    @Test
    public void equals() {
        // Test for pets
        PetNameContainsKeywordsPredicate firstPredicate =
                new PetNameContainsKeywordsPredicate(Collections.singletonList("first"));
        PetNameContainsKeywordsPredicate secondPredicate =
                new PetNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPetCommand findFirstCommand = new FindPetCommand(firstPredicate);
        FindPetCommand findSecondCommand = new FindPetCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPetCommand findFirstCommandCopy = new FindPetCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different pet -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPetFound() {
        String expectedMessage = String.format(MESSAGE_PETS_LISTED_OVERVIEW, 0);
        PetNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPetCommand command = new FindPetCommand(predicate);
        expectedModel.updateFilteredPetList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPetList());
    }

    @Test
    public void execute_multipleKeywords_multiplePetsFound() {
        String expectedMessage = String.format(MESSAGE_PETS_LISTED_OVERVIEW, 3);
        PetNameContainsKeywordsPredicate predicate = preparePredicate("Bison Max Milo");
        FindPetCommand command = new FindPetCommand(predicate);
        expectedModel.updateFilteredPetList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPets.MAX, TypicalPets.MILO, TypicalPets.BISON),
            model.getFilteredPetList());
    }

    @Test
    public void toStringMethod() {
        PetNameContainsKeywordsPredicate predicate = new PetNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindPetCommand findCommand = new FindPetCommand(predicate);
        String expected = FindPetCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PetNameContainsKeywordsPredicate}.
     */
    private PetNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PetNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
