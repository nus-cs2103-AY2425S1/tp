package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for PriorityCommand.
 */
public class PriorityCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidPriorityLevelUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withPriorityLevel(VALID_PRIORITY_LEVEL).build();

        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), VALID_PRIORITY_LEVEL);

        String expectedMessage = String.format("Priority level %d successfully set for %s", VALID_PRIORITY_LEVEL,
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(priorityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final PriorityCommand standardCommand = new PriorityCommand(INDEX_FIRST_PERSON.getZeroBased(), 1);

        // same values -> returns true
        PriorityCommand commandWithSameValues = new PriorityCommand(INDEX_FIRST_PERSON.getZeroBased(), 1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PriorityCommand(INDEX_SECOND_PERSON.getZeroBased(), 1)));

        // different priorityLevel -> returns false
        assertFalse(standardCommand.equals(new PriorityCommand(INDEX_FIRST_PERSON.getZeroBased(), 2)));
    }
}
