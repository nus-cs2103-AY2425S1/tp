package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_TENTH_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, Messages.format(personToView));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true, false, false);

        assertCommandSuccess(viewCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_outOfBoundsIndexUnfilteredList_throwsCommandException() {
        ViewCommand viewCommand = new ViewCommand(INDEX_TENTH_PERSON);
        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_outOfBoundsIndexFilteredList_throwsCommandException() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);

        ModelManager filteredModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        filteredModel.updateFilteredPersonList(Model.PREDICATE_SHOW_NONE_PERSONS);

        assertCommandFailure(viewCommand, filteredModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equalsMethod() {
        ViewCommand viewCommandFirst = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewCommandSecond = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(viewCommandFirst, viewCommandFirst);
        assertEquals(viewCommandSecond, viewCommandSecond);

        // same index -> returns true
        assertEquals(viewCommandFirst, new ViewCommand(INDEX_FIRST_PERSON));
        assertEquals(viewCommandSecond, new ViewCommand(INDEX_SECOND_PERSON));

        // different index -> returns false
        assertNotEquals(viewCommandFirst, viewCommandSecond);

        // not of type ViewCommand -> returns false
        assertNotEquals(viewCommandFirst, "first");
        assertNotEquals(viewCommandSecond, 2);

        // null value -> returns false
        assertNotEquals(viewCommandFirst, null);
    }

    @Test
    public void toStringMethod() {
        assertEquals(ViewCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON + "}",
                new ViewCommand(INDEX_FIRST_PERSON).toString());
    }
}
