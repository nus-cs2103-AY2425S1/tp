package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Test class for export command
 */
public class ExportCommandTest {

    private static final Path projectRootPath = Paths.get(System.getProperty("user.dir"));

    private final Path exportFilePath =
        projectRootPath.resolve("src").resolve("test").resolve("data").resolve("JsonExportTest")
                                                                        .resolve("exported_data.csv");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());



    @Test
    public void execute_exportCommand_successfulExport() throws CommandException {
        try {
            Path importPath = projectRootPath.resolve("src").resolve("test").resolve("data")
                                .resolve("JsonExportTest").resolve("typicalPersonsAddressBook.json");
            ExportCommand exportCommand = new ExportCommand();

            // Execute the export command
            CommandResult commandResult = exportCommand.execute(model, importPath , exportFilePath);
            // Verify the export is successful
            assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS),
                    commandResult.getFeedbackToUser());

            // Verify the exported file exists
            Path exportedFilePath = Paths.get(exportFilePath.toString());
            assertTrue(Files.exists(exportedFilePath));

            Files.deleteIfExists(exportedFilePath);
        } catch (IOException e) {
            // Handle any IO exception
            e.printStackTrace();
        }
    }
}
