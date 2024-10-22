package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.model.Model;

/**
 * Exports the address book data to a specified location.
 * The data is saved in JSON format.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = ":export";
    public static final String MESSAGE_SUCCESS = "Address book has been exported!";
    public static final String COMMAND_SUMMARY_ACTION = "Export";
    public static final String COMMAND_SUMMARY_FORMAT = ":export";
    public static final String COMMAND_SUMMARY_EXAMPLES = ":export";
    public static final String NO_DESTINATION_MESSAGE = "No destination selected. Export cancelled.";
    private File destinationFile;

    // Constructor for normal use (opens file chooser)
    public ExportCommand() {}

    // Constructor for testing (bypasses file chooser)
    public ExportCommand(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    /**
     * Opens a file chooser dialog to select the destination for the export.
     * The data is then copied to the selected location.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult indicating the result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        final File sourceFile = new File("data/addressbook.json");

        if (destinationFile == null) {
            // If no destination file is set, show file chooser asynchronously
            // This will be the main scenario except for testing.
            Platform.runLater(() -> {
                destinationFile = chooseExportLocation(new Stage());
                if (destinationFile == null) {
                    // User cancels the export
                    System.out.println(NO_DESTINATION_MESSAGE);
                }
                // Perform the export only after a valid destination is chosen
                performExport(sourceFile, destinationFile);
            });
            // Return immediately since file selection is asynchronous
            return new CommandResult("Export process started. Please select a file location.");
        } else {
            // If the destination is already set (e.g., for testing), perform the export directly
            return performExport(sourceFile, destinationFile);
        }
    }

    /**
     * Copies the address book data to the specified destination file.
     * @param sourceFile The source JSON file.
     * @param destinationFile The destination file chosen by the user.
     * @return A CommandResult indicating the success or failure of the export.
     */
    private CommandResult performExport(File sourceFile, File destinationFile) {
        try {
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return new CommandResult(MESSAGE_SUCCESS + " Data saved to: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            return new CommandResult("Error exporting data: " + e.getMessage());
        }
    }

    /**
     * Opens a file chooser dialog to select the destination for the export.
     * @param stage
     * @return File location to export to
     */
    public File chooseExportLocation(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Address Book");

        // Set the initial file name
        fileChooser.setInitialFileName("addressbook.json");

        // Filter to only show JSON files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        // Show the save dialog and return the selected file
        return fileChooser.showSaveDialog(stage);
    }

    public void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
    }
}
