package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class UpdateCommandTest {

    @TempDir
    public Path temporaryFolder;
    private Model model;

    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
                temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
    }

    @Test
    public void execute_noFieldSpecified_failure() {
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdateCommand.UpdatePersonDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(new Nric("S1234567D"), descriptor);

        assertThrows(CommandException.class, () -> updateCommand.execute(model), UpdateCommand.MESSAGE_NOT_EDITED);
    }

    /**
     * Tests the successful execution of editing all fields in an unfiltered list.
     */
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws IOException {
        Person editedPerson = new PersonBuilder().build();
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(editedPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getStorage());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws IOException {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        UpdateCommand updateCommand = new UpdateCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getStorage());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws IOException {
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, new UpdateCommand.UpdatePersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getStorage());

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws IOException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getStorage());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(firstPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new UpdatePersonDescriptorBuilder(personInList).build());

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex,
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        UpdateCommand.UpdatePersonDescriptor editPersonDescriptor = new UpdateCommand.UpdatePersonDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(index, editPersonDescriptor);
        String expected = UpdateCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, updateCommand.toString());
    }

    @Test
    public void equals() {

        final UpdateCommand standardCommand1 = new UpdateCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        UpdateCommand.UpdatePersonDescriptor copyDescriptor1 = new UpdateCommand.UpdatePersonDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues1 = new UpdateCommand(INDEX_FIRST_PERSON, copyDescriptor1);
        assertTrue(standardCommand1.equals(commandWithSameValues1));

        // same object -> returns true
        assertTrue(standardCommand1.equals(standardCommand1));

        // null -> returns false
        assertFalse(standardCommand1.equals(null));

        // different types -> returns false
        assertFalse(standardCommand1.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand1.equals(new UpdateCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand1.equals(new UpdateCommand(INDEX_FIRST_PERSON, DESC_BOB)));

        final UpdateCommand standardCommand2 = new UpdateCommand(new Nric("S1234567D"),
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                        .withAge(VALID_AGE_AMY).withPhone(VALID_PHONE_AMY).build());

        // same values -> returns true
        UpdateCommand.UpdatePersonDescriptor copyDescriptor2 = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY).withAge(VALID_AGE_AMY).withPhone(VALID_PHONE_AMY)
                .build();
        UpdateCommand commandWithSameValues2 = new UpdateCommand(new Nric("S1234567D"), copyDescriptor2);
        assertEquals(standardCommand2, commandWithSameValues2);

        // different NRIC -> returns false
        UpdateCommand commandWithDifferentNric = new UpdateCommand(new Nric("S7654321D"), copyDescriptor2);
        assertNotEquals(standardCommand2, commandWithDifferentNric);

        // different descriptor -> returns false
        UpdateCommand commandWithDifferentDescriptor = new UpdateCommand(new Nric("S1234567D"),
                new UpdatePersonDescriptorBuilder().withName("Bob").build());
        assertNotEquals(standardCommand2, commandWithDifferentDescriptor);
    }
}
