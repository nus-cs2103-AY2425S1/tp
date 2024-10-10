package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CsvUtil;
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
        AddressBook addressBook = new AddressBook(); Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build(); addressBook.addPerson(alice);
        addressBook.addPerson(bob); Model model = new ModelManager(addressBook, new UserPrefs());

        // Execute the DownloadCommand
        DownloadCommand downloadCommand = new DownloadCommand(); CommandResult result = downloadCommand.execute(model);

        // Verify the result message
        assertEquals(DownloadCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Assume the CSV is saved to "data/exported.csv"
        Path csvFilePath = Paths.get("data", "exported.csv"); assertTrue(Files.exists(csvFilePath));

        // Read the saved CSV content
        String savedCsvContent = new String(Files.readAllBytes(csvFilePath));

        // Generate expected CSV content
        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        String expectedCsvContent = CsvUtil.convertObservableListToCsv(personList);

        // Verify the CSV content matches
        assertEquals(expectedCsvContent, savedCsvContent);
    }
}
