package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DownloadCommandTest {

    @Test
    public void execute_downloadCommand_success() throws Exception {
        // Set up the model with sample data
        AddressBook addressBook = new AddressBook();
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        addressBook.addPerson(alice);
        addressBook.addPerson(bob);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Execute the DownloadCommand
        DownloadCommand downloadCommand = new DownloadCommand();
        CommandResult result = downloadCommand.execute(model);

        // Verify the result message
        assertEquals(DownloadCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

    }
}
