package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MarkVipCommand.
 */
public class MarkVipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Person setVip(Person person, boolean newState) {
        PersonBuilder pb = new PersonBuilder(person);
        return pb.withVipState(newState).build();
    }

    @Test
    public void execute_trueUnfilteredList_success() {
        Person personToVip = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        MarkVipCommand markVipCommand = new MarkVipCommand(INDEX_THIRD_PERSON, true);
        Person targetPerson = setVip(personToVip, true);
        String expectedMessage = String.format(MarkVipCommand.MESSAGE_VIP_PERSON_SUCCESS, Messages.format(personToVip));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToVip, targetPerson);

        assertCommandSuccess(markVipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_falseUnfilteredList_success() {
        Person personStartingAsVip = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personToUnvip = setVip(personStartingAsVip, true);
        model.setPerson(personStartingAsVip, personToUnvip);
        Person targetPerson = setVip(personToUnvip, false);
        MarkVipCommand markVipCommand = new MarkVipCommand(INDEX_FIRST_PERSON, false);
        // for some reason, updating Carl causes him to move to the front of the list... feature not bug?
        String expectedMessage = String.format(MarkVipCommand.MESSAGE_UNVIP_PERSON_SUCCESS,
                Messages.format(personToUnvip));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToUnvip, targetPerson);

        assertCommandSuccess(markVipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_trueUnfilteredList_obsolete() {
        Person personStartingAsVip = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToVip = setVip(personStartingAsVip, true);
        model.setPerson(personStartingAsVip, personToVip);
        MarkVipCommand markVipCommand = new MarkVipCommand(INDEX_FIRST_PERSON, true);
        String expectedMessage = String.format(MarkVipCommand.MESSAGE_VIP_PERSON_OBSOLETE,
                Messages.format(personToVip));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(markVipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_falseUnfilteredList_obsolete() {
        Person personStartingAsNonVip = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person personToUnvip = setVip(personStartingAsNonVip, false);
        model.setPerson(personStartingAsNonVip, personToUnvip);
        MarkVipCommand markVipCommand = new MarkVipCommand(INDEX_THIRD_PERSON, false);
        String expectedMessage = String.format(MarkVipCommand.MESSAGE_UNVIP_PERSON_OBSOLETE,
                Messages.format(personToUnvip));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(markVipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkVipCommand markVipCommand = new MarkVipCommand(outOfBoundIndex, false);

        assertCommandFailure(markVipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Update filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkVipCommand markVipCommand = new MarkVipCommand(outOfBoundIndex, true);

        assertCommandFailure(markVipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final MarkVipCommand standardCommand = new MarkVipCommand(INDEX_FIRST_PERSON, true);

        // same values -> returns true
        MarkVipCommand commandWithSameValues = new MarkVipCommand(INDEX_FIRST_PERSON, true);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MarkVipCommand(INDEX_SECOND_PERSON, true)));

        // different target state -> returns false
        assertFalse(standardCommand.equals(new MarkVipCommand(INDEX_FIRST_PERSON, false)));
    }
}
