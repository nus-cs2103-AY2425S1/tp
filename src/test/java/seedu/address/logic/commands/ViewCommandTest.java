package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_FIELD_MISSING;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_INDEX_NOT_FOUND;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_NAME_NOT_FOUND;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_VIEW_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewSecondCommand = new ViewCommand(new Name("Alice Pauline"));

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        assertTrue(viewFirstCommand.equals(new ViewCommand(INDEX_FIRST_PERSON)));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // different values -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        // index
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        String expected = ViewCommand.class.getCanonicalName() + "{index to view=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, viewCommand.toString());

        // name
        viewCommand = new ViewCommand(new Name("Alice Pauline"));
        expected = ViewCommand.class.getCanonicalName() + "{person to view=" + "Alice Pauline" + "}";
        assertEquals(expected, viewCommand.toString());
    }

    @Test
    public void execute_validIndex_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index targetIndex = INDEX_FIRST_PERSON;
        ViewCommand command = new ViewCommand(targetIndex);
        String expectedMessage = String.format(MESSAGE_VIEW_SUCCESS, personToView.getName());
        expectedModel.viewNote(personToView);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validName_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewCommand command = new ViewCommand(personToView.getName());
        String expectedMessage = String.format(MESSAGE_VIEW_SUCCESS, personToView.getName());
        expectedModel.viewNote(personToView);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand command = new ViewCommand(invalidIndex);

        assertCommandFailure(command, model, MESSAGE_INDEX_NOT_FOUND);

    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        Name invalidName = new Name("Nonexistent Name");
        ViewCommand command = new ViewCommand(invalidName);

        assertCommandFailure(command, model, MESSAGE_NAME_NOT_FOUND);
    }

    @Test
    public void execute_null_throwsCommandException() {
        ViewCommand command = new ViewCommand((Name) null);

        assertCommandFailure(command, model, MESSAGE_FIELD_MISSING);
    }
}
