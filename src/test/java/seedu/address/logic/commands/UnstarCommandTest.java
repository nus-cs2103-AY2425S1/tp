package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StarredStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnstarCommand.
 */
public class UnstarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        Person personToUnstar = model.getFilteredPersonList().get(Index.fromZeroBased(0).getZeroBased());
        Person starredPerson = new Person(
                personToUnstar.getName(),
                personToUnstar.getPhone(),
                personToUnstar.getEmail(),
                personToUnstar.getAddress(),
                personToUnstar.getAge(),
                personToUnstar.getSex(),
                personToUnstar.getAppointment(),
                personToUnstar.getTags(),
                personToUnstar.getNote(),
                new StarredStatus("true"));

        model.setPerson(personToUnstar, starredPerson);

        UnstarCommand unstarCommand = new UnstarCommand(Index.fromZeroBased(0));

        CommandResult commandResult = unstarCommand.execute(model);

        assertEquals(String.format(UnstarCommand.MESSAGE_UNSTAR_PERSON_SUCCESS, starredPerson.getName()),
                commandResult.getFeedbackToUser());

        Person unstarredPerson = model.getFilteredPersonList().get(Index.fromZeroBased(0).getZeroBased());
        assertEquals(new StarredStatus("false"), unstarredPerson.getStarredStatus());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnstarCommand unstarCommand = new UnstarCommand(invalidIndex);

        assertCommandFailure(unstarCommand, model, StarCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validNameFilteredList_success() throws CommandException {
        Person personToUnstar = model.getFilteredPersonList().get(0);
        Person starredPerson = new Person(
                personToUnstar.getName(),
                personToUnstar.getPhone(),
                personToUnstar.getEmail(),
                personToUnstar.getAddress(),
                personToUnstar.getAge(),
                personToUnstar.getSex(),
                personToUnstar.getAppointment(),
                personToUnstar.getTags(),
                personToUnstar.getNote(),
                new StarredStatus("true"));

        model.setPerson(personToUnstar, starredPerson);

        Name targetName = personToUnstar.getName();
        UnstarCommand unstarCommand = new UnstarCommand(targetName);

        CommandResult commandResult = unstarCommand.execute(model);

        assertEquals(String.format(UnstarCommand.MESSAGE_UNSTAR_PERSON_SUCCESS, starredPerson.getName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Name invalidName = new Name("Nonexistent Name");
        UnstarCommand unstarCommand = new UnstarCommand(invalidName);

        assertCommandFailure(unstarCommand, model, String.format(UnstarCommand.MESSAGE_PERSON_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnstarCommand unstarCommand = new UnstarCommand(outOfBoundIndex);
        assertCommandFailure(unstarCommand, model, UnstarCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_negativeIndex_throwsIndexOutOfBoundsException() {
        int negativeIndexValue = -1;
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Index negativeIndex = Index.fromOneBased(negativeIndexValue);
            new UnstarCommand(negativeIndex);
        });
    }

    @Test
    public void equals() {
        UnstarCommand unstarNameCommand = new UnstarCommand(new Name("Alice"));
        UnstarCommand unstarNameCommandCopy = new UnstarCommand(new Name("Alice"));
        UnstarCommand unstarIndexCommand = new UnstarCommand(Index.fromOneBased(1));

        // same object -> returns true
        assertTrue(unstarNameCommand.equals(unstarNameCommand));

        // same values -> returns true
        assertTrue(unstarNameCommand.equals(unstarNameCommandCopy));

        // different types -> returns false
        assertFalse(unstarNameCommand.equals(1));

        // null -> returns false
        assertFalse(unstarNameCommand.equals(null));

        // different names -> returns false
        assertFalse(unstarNameCommand.equals(unstarIndexCommand));

        // different index -> returns false
        assertFalse(unstarIndexCommand.equals(unstarNameCommand));
    }

    @Test
    public void toStringMethod() {
        // Testing with name
        Name targetName = new Name("John Doe");
        UnstarCommand unstarCommandByName = new UnstarCommand(targetName);
        String expectedNameString = String.format("UnstarCommand[targetName=%s]", targetName);
        assertEquals(expectedNameString, unstarCommandByName.toString());

        // Testing with index
        Index targetIndex = Index.fromOneBased(1);
        UnstarCommand unstarCommandByIndex = new UnstarCommand(targetIndex);
        String expectedIndexString = String.format("UnstarCommand[targetIndex=%d]", targetIndex.getOneBased());
        assertEquals(expectedIndexString, unstarCommandByIndex.toString());
    }

    @Test
    public void execute_personAlreadyUnstarred_throwsCommandException() {
        Person personToUnstar = model.getFilteredPersonList().get(0);
        Person alreadyUnstarredPerson = new Person(
                personToUnstar.getName(),
                personToUnstar.getPhone(),
                personToUnstar.getEmail(),
                personToUnstar.getAddress(),
                personToUnstar.getAge(),
                personToUnstar.getSex(),
                personToUnstar.getAppointment(),
                personToUnstar.getTags(),
                personToUnstar.getNote(),
                new StarredStatus("false"));
        model.setPerson(personToUnstar, alreadyUnstarredPerson);

        UnstarCommand unstarCommand = new UnstarCommand(alreadyUnstarredPerson.getName());

        assertCommandFailure(unstarCommand, model,
                String.format(UnstarCommand.MESSAGE_ALREADY_UNSTARRED, alreadyUnstarredPerson.getName()));
    }
}
