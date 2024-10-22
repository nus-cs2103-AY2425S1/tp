package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndices;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class PinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToPin = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        PinCommand pinCommand = new PinCommand(List.of(INDEX_SECOND_PERSON));

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS,
                Messages.format(personToPin));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.pinPerson(personToPin);
        expectedModel.sortByPin();

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = new PinCommand(List.of(outOfBoundIndex));

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndices(model, List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        Person personToPin = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        PinCommand pinCommand = new PinCommand(List.of(INDEX_SECOND_PERSON));

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS,
                Messages.format(personToPin));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndices(expectedModel, List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        expectedModel.pinPerson(personToPin);
        expectedModel.sortByPin();

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PinCommand pinCommand = new PinCommand(List.of(outOfBoundIndex));

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
