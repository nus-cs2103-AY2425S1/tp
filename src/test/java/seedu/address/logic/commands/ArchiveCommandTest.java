package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VEGETARIAN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveCommand.ArchivePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.ArchivePersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        Person editedPerson = new PersonBuilder().build();
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
//        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
//
//        PersonBuilder personInList = new PersonBuilder(lastPerson);
//        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
//                .withTags(VALID_TAG_VEGETARIAN).build();
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
//                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_VEGETARIAN).build();
//        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.setPerson(lastPerson, editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, new ArchivePersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON,
                new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchivePersonDescriptor descriptor = new ArchivePersonDescriptorBuilder(firstPerson).build();
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(archiveCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON,
                new ArchivePersonDescriptorBuilder(personInList).build());

        assertCommandFailure(archiveCommand, model, ArchiveCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchivePersonDescriptor descriptor = new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex, descriptor);

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

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex,
                new ArchivePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
//
//    @Test
//    public void equals() {
//        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);
//
//        // same values -> returns true
//        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
//        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
//        assertTrue(standardCommand.equals(commandWithSameValues));
//
//        // same object -> returns true
//        assertTrue(standardCommand.equals(standardCommand));
//
//        // null -> returns false
//        assertFalse(standardCommand.equals(null));
//
//        // different types -> returns false
//        assertFalse(standardCommand.equals(new ClearCommand()));
//
//        // different index -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
//
//        // different descriptor -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
//    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ArchivePersonDescriptor archivePersonDescriptor = new ArchivePersonDescriptor();
        ArchiveCommand archiveCommand = new ArchiveCommand(index, archivePersonDescriptor);
        String expected = ArchiveCommand.class.getCanonicalName() + "{index=" + index + ", archivePersonDescriptor="
                + archivePersonDescriptor + "}";
        assertEquals(expected, archiveCommand.toString());
    }

}
