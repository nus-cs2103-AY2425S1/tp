package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
                                                                temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Person editedPerson = new PersonBuilder().withNric("S1234567Z").build();
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(editedPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(editedPerson.getNric(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), model.getStorage());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_failure() {
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdateCommand.UpdatePersonDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(new Nric("S1234567D"), descriptor);

        assertThrows(CommandException.class, () -> updateCommand.execute(model), UpdateCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(new Nric("S1234567D"),
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                        .withAge(VALID_AGE_AMY).build());

        // same values -> returns true
        UpdateCommand.UpdatePersonDescriptor copyDescriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY).withAge(VALID_AGE_AMY).build();
        UpdateCommand commandWithSameValues = new UpdateCommand(new Nric("S1234567D"), copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // different NRIC -> returns false
        UpdateCommand commandWithDifferentNric = new UpdateCommand(new Nric("S7654321D"), copyDescriptor);
        assertThrows(AssertionError.class, () -> assertEquals(standardCommand, commandWithDifferentNric));
    }
}
