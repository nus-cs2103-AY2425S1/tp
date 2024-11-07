package seedu.address.logic.commands.volunteercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalVolunteers.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.VolunteerNameContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FindVolunteerCommand}.
 */
public class FindVolunteerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_volunteersFound_success() {
        // Search for a volunteer name that exists in the volunteer list
        String searchString = "Alice"; // Replace with an actual volunteer name in getTypicalVolunteerList()
        VolunteerNameContainsKeywordPredicate predicate = new VolunteerNameContainsKeywordPredicate(searchString);
        FindVolunteerCommand command = new FindVolunteerCommand(predicate);

        // Update expected model to show volunteers containing the search string
        expectedModel.updateFilteredVolunteerList(predicate);

        String expectedMessage = String.format(FindVolunteerCommand.MESSAGE_VOLUNTEER_FOUND,
                expectedModel.getFilteredVolunteerList().size(), searchString);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noVolunteersFound_failure() {
        // Search for a name that does not match any volunteers
        String searchString = "NonExistentName";
        VolunteerNameContainsKeywordPredicate predicate = new VolunteerNameContainsKeywordPredicate(searchString);
        FindVolunteerCommand command = new FindVolunteerCommand(predicate);

        String expectedMessage = String.format(FindVolunteerCommand.MESSAGE_VOLUNTEER_NOT_FOUND, searchString);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySearchString_throwsCommandException() {
        String searchString = ""; // Empty search string
        VolunteerNameContainsKeywordPredicate predicate = new VolunteerNameContainsKeywordPredicate(searchString);
        FindVolunteerCommand command = new FindVolunteerCommand(predicate);

        assertCommandFailure(command, model, String.format(FindVolunteerCommand.MESSAGE_VOLUNTEER_NOT_FOUND,
                searchString));
    }

    @Test
    public void equals() {
        VolunteerNameContainsKeywordPredicate predicateAlice = new VolunteerNameContainsKeywordPredicate("Alice");
        VolunteerNameContainsKeywordPredicate predicateBob = new VolunteerNameContainsKeywordPredicate("Bob");
        FindVolunteerCommand findAliceCommand = new FindVolunteerCommand(predicateAlice);
        FindVolunteerCommand findBobCommand = new FindVolunteerCommand(predicateBob);

        // same object -> returns true
        assertTrue(findAliceCommand.equals(findAliceCommand));

        // same values -> returns true
        FindVolunteerCommand findAliceCommandCopy = new FindVolunteerCommand(predicateAlice);
        assertTrue(findAliceCommand.equals(findAliceCommandCopy));

        // different types -> returns false
        assertFalse(findAliceCommand.equals(1));

        // null -> returns false
        assertFalse(findAliceCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findAliceCommand.equals(findBobCommand));
    }

    @Test
    public void toStringMethod() {
        String searchString = "Alice";
        VolunteerNameContainsKeywordPredicate predicate = new VolunteerNameContainsKeywordPredicate(searchString);
        FindVolunteerCommand findVolunteerCommand = new FindVolunteerCommand(predicate);

        String expected = "FindVolunteerCommand[predicate=" + predicate.toString() + "]";
        assertEquals(expected, findVolunteerCommand.toString());
    }
}
