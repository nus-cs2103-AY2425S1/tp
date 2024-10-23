package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_BOB;
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
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ModCommandTest {

    private static final String MODULE_NAME_STUB = "CS1101S";
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addModNameUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withModuleName(MODULE_NAME_STUB).build();

        ModCommand modCommand = new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(editedPerson.getModuleName().toString()));

        String expectedMessage = String.format(ModCommand.MESSAGE_ADD_MODULE_NAME_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(modCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteModNameUnfiliteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withModuleName("").build();

        ModCommand modCommand = new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(editedPerson.getModuleName().toString()));

        String expectedMessage = String.format(ModCommand.MESSAGE_DELETE_MODULE_NAME_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(modCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withModuleName(MODULE_NAME_STUB).build();

        ModCommand modCommand = new ModCommand(INDEX_FIRST_PERSON, new ModuleName(editedPerson.getModuleName()
                .toString()));

        String expectedMessage = String.format(ModCommand.MESSAGE_ADD_MODULE_NAME_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(modCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfiliteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ModCommand modCommand = new ModCommand(outOfBoundIndex, new ModuleName(VALID_MODNAME_BOB));

        assertCommandFailure(modCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ModCommand modCommand = new ModCommand(outOfBoundIndex, new ModuleName(VALID_MODNAME_AMY));
        assertCommandFailure(modCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ModCommand standardCommand = new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(VALID_MODNAME_AMY));

        // same values -> returns true
        ModCommand commandWithSameValues = new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(VALID_MODNAME_AMY));
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new ModCommand(INDEX_SECOND_PERSON,
                new ModuleName(VALID_MODNAME_AMY)));

        // different remark -> returns false
        assertNotEquals(standardCommand, new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(VALID_MODNAME_BOB)));
    }



}
