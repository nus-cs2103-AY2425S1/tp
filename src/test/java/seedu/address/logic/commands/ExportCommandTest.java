package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_fileDoesNotExist_success() {
        String path = ExportCommand.PATH;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        assertCommandSuccess(new ExportCommand(), model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(file.exists());
    }

    @Test
    public void execute_typicalAddressBook_success() throws IOException {
        String path = ExportCommand.PATH;
        assertCommandSuccess(new ExportCommand(), model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
        File exportedContacts = new File(path);
        File expectedExportedContacts = new File("./src/test/data/CsvTest/ExpectedExportedContacts.csv");

        String line;
        String expectedLine;
        FileReader fr1 = new FileReader(exportedContacts);
        FileReader fr2 = new FileReader(expectedExportedContacts);
        BufferedReader br1 = new BufferedReader(fr1);
        BufferedReader br2 = new BufferedReader(fr2);
        while ((expectedLine = br2.readLine()) != null) {
            System.out.println(expectedLine);
            line = br1.readLine();
            assertTrue(line.equals(expectedLine));
        }
    }
}
