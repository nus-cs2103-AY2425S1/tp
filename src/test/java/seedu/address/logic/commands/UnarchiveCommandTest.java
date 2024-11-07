package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ArchiveCommand.ArchivePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.ArchivePersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UnarchiveCommand archiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON, new ArchivePersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(archiveCommand, expectedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        UnarchiveCommand archiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON,
                new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(archiveCommand, expectedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchivePersonDescriptor descriptor = new ArchivePersonDescriptorBuilder(firstPerson).build();
        UnarchiveCommand archiveCommand = new UnarchiveCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(archiveCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        UnarchiveCommand archiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON,
                new ArchivePersonDescriptorBuilder(personInList).build());

        assertCommandFailure(archiveCommand, model, ArchiveCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchivePersonDescriptor descriptor = new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UnarchiveCommand archiveCommand = new UnarchiveCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        UnarchiveCommand archiveCommand = new UnarchiveCommand(outOfBoundIndex,
                new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        // same values -> returns true
        ArchivePersonDescriptor copyDescriptor = new ArchivePersonDescriptor();
        final UnarchiveCommand standardCommand = new UnarchiveCommand(INDEX_FIRST_PERSON, copyDescriptor);
        UnarchiveCommand commandWithSameValues = new UnarchiveCommand(INDEX_FIRST_PERSON, copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ArchiveCommand(INDEX_SECOND_PERSON, copyDescriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ArchiveCommand(INDEX_FIRST_PERSON,
                new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build())));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ArchivePersonDescriptor archivePersonDescriptor = new ArchivePersonDescriptor();
        UnarchiveCommand archiveCommand = new UnarchiveCommand(index, archivePersonDescriptor);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{index=" + index + ", archivePersonDescriptor="
                + archivePersonDescriptor + "}";
        assertEquals(expected, archiveCommand.toString());
    }

}
