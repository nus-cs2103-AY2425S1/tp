package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.model.Model;
import tuteez.model.ModelManager;
import tuteez.model.UserPrefs;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DisplayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDisplay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DisplayCommand displayCommand = new DisplayCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DisplayCommand.MESSAGE_DISPLAY_PERSON_SUCCESS,
                Messages.formatPersonName(personToDisplay));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.displayPerson(personToDisplay);

        assertCommandSuccess(displayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayCommand displayCommand = new DisplayCommand(outOfBoundIndex);

        assertCommandFailure(displayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDisplay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DisplayCommand displayCommand = new DisplayCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DisplayCommand.MESSAGE_DISPLAY_PERSON_SUCCESS,
                Messages.formatPersonName(personToDisplay));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        expectedModel.displayPerson(personToDisplay);

        assertCommandSuccess(displayCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DisplayCommand displayCommand = new DisplayCommand(outOfBoundIndex);

        assertCommandFailure(displayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validName_success() {
        Person personAdded = new PersonBuilder().withName(VALID_NAME_AMY).build();
        model.addPerson(personAdded);

        Name targetName = personAdded.getName();

        Person personToDisplay = model.findPersonByName(targetName);
        DisplayCommand displayCommand = new DisplayCommand(targetName);

        String expectedMessage = String.format(DisplayCommand.MESSAGE_DISPLAY_PERSON_SUCCESS,
                Messages.formatPersonName(personToDisplay));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.displayPerson(personToDisplay);

        assertCommandSuccess(displayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        Person personAdded = new PersonBuilder().withName(VALID_NAME_AMY).build();
        model.addPerson(personAdded);
        Name invalidName = new Name("Amyy Beee");

        DisplayCommand displayCommand = new DisplayCommand(invalidName);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME,
                invalidName);
        assertCommandFailure(displayCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DisplayCommand displayFirstCommand = new DisplayCommand(INDEX_FIRST_PERSON);
        DisplayCommand displaySecondCommand = new DisplayCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(displayFirstCommand.equals(displayFirstCommand));

        // same values -> returns true
        DisplayCommand displayFirstCommandCopy = new DisplayCommand(INDEX_FIRST_PERSON);
        assertTrue(displayFirstCommand.equals(displayFirstCommandCopy));

        // different types -> returns false
        assertFalse(displayFirstCommand.equals(1));

        // null -> returns false
        assertFalse(displayFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(displayFirstCommand.equals(displaySecondCommand));
    }

    @Test
    public void toString_validTargetIndex_success() {
        // Create a DisplayCommand instance with an index
        Index targetIndex = Index.fromOneBased(1);
        Name targetName = null;
        DisplayCommand displayCommand = new DisplayCommand(targetIndex);

        String expectedString = new ToStringBuilder(displayCommand)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();

        assertEquals(expectedString, displayCommand.toString());
    }

    @Test
    public void toString_validTargetName_success() {
        // Create a DisplayCommand instance with both a name
        Index targetIndex = null;
        Name targetName = null;
        DisplayCommand displayCommand = new DisplayCommand(targetIndex);

        String expectedString = new ToStringBuilder(displayCommand)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();

        assertEquals(expectedString, displayCommand.toString());
    }
}
