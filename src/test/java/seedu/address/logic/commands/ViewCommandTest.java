package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ViewCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person viewedPerson = new PersonBuilder(personInFilteredList).build();
        Index index = Index.fromOneBased(1);
        List<Person> associatedPeople = model.getAssociatedPeople(viewedPerson);
        String expectedCommandResult = String.format(ViewCommand.MESSAGE_SUCCESS,
                Messages.format(viewedPerson, associatedPeople));
        assertCommandSuccess(new ViewCommand(index), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);
        CommandTestUtil.assertCommandFailure(viewCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Index index = Index.fromOneBased(1);
        Index differentIndex = Index.fromOneBased(2);
        ViewCommand viewCommand = new ViewCommand(index);
        ViewCommand differentViewCommand = new ViewCommand(differentIndex);
        // same object -> returns true
        assert(viewCommand.equals(viewCommand));
        // same values -> returns true
        ViewCommand viewCommandCopy = new ViewCommand(index);
        assert(viewCommand.equals(viewCommandCopy));
        // different types -> returns false
        assert(!viewCommand.equals(1));
        // null -> returns false
        assert(!viewCommand.equals(null));
        // different person -> returns false
        assert(!viewCommand.equals(differentViewCommand));
    }

}
