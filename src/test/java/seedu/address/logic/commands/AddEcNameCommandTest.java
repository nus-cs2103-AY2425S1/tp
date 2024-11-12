package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EcName;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddEcNameCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addEcNameUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEcName(VALID_ECNAME_AMY).build();
        AddEcNameCommand addEcNameCommand = new AddEcNameCommand(INDEX_FIRST_PERSON,
                new EcName(editedPerson.getEcName().value));
        String expectedMessage = String.format(AddEcNameCommand.MESSAGE_ADD_ECNAME_SUCCESS,
                editedPerson.getName(), editedPerson.getEcName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addEcNameCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteEcNameUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEcName("").build();
        AddEcNameCommand ecNameCommand = new AddEcNameCommand(INDEX_FIRST_PERSON,
                new EcName(editedPerson.getEcName().toString()));
        String expectedMessage = String.format(AddEcNameCommand.MESSAGE_DELETE_ECNAME_SUCCESS,
                editedPerson.getName(), editedPerson.getEcName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(ecNameCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withEcName(VALID_ECNAME_AMY).build();
        AddEcNameCommand ecNameCommand = new AddEcNameCommand(INDEX_FIRST_PERSON,
                new EcName(editedPerson.getEcName().value));
        String expectedMessage = String.format(AddEcNameCommand.MESSAGE_ADD_ECNAME_SUCCESS,
                editedPerson.getName(), editedPerson.getEcName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(ecNameCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddEcNameCommand ecNameCommand = new AddEcNameCommand(outOfBoundIndex,
                new EcName(VALID_ECNAME_BOB));
        assertCommandFailure(ecNameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddEcNameCommand ecNameCommand = new AddEcNameCommand(outOfBoundIndex,
                new EcName(VALID_ECNAME_BOB));

        assertCommandFailure(ecNameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddEcNameCommand standardCommand = new AddEcNameCommand(
                INDEX_FIRST_PERSON, new EcName(VALID_ECNAME_AMY));

        // EP: same values -> returns true
        AddEcNameCommand commandWithSameValues = new AddEcNameCommand(
                INDEX_FIRST_PERSON, new EcName(VALID_ECNAME_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // EP: same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // EP: null -> returns false
        assertFalse(standardCommand.equals(null));

        // EP: different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // EP: different person name -> returns false
        assertFalse(standardCommand.equals(new AddEcNameCommand(INDEX_SECOND_PERSON,
                new EcName(VALID_ECNAME_AMY))));

        // EP: different emergency contact name -> returns false
        assertFalse(standardCommand.equals(new AddEcNameCommand(INDEX_FIRST_PERSON,
                new EcName(VALID_ECNAME_BOB))));
    }
}

