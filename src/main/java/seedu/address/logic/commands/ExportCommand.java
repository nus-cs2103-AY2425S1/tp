package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.security.EncryptionManager;

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
    private File sourceFile;
    private String keyPath;

    // Constructor for normal use (opens file chooser)
    public ExportCommand() {
        this(null, new File("data/addressbook.json"), null);
    }

    /**
     * Constructs an {@code ExportCommand} with the specified destination file, source file, and key path.
     * This is for testing where destination file is hardcoded.
     *
     * @param destinationFile the file where the data will be exported to
     * @param sourceFile      the temporary file to be exported
     * @param keyPath         the path to the key (if required for the export process)
     */
    public ExportCommand(File destinationFile, File sourceFile, String keyPath) {
        this.destinationFile = destinationFile;
        this.sourceFile = sourceFile;
        this.keyPath = keyPath;
    }

    /**
     * Opens a file chooser dialog to select the destination for the export.
     * The data is then copied to the selected location.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult indicating the result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        byte[] encryptedData;
        try {
            // Decrypt data on attempting to export
            encryptedData = Files.readAllBytes(sourceFile.toPath());
            String jsonData = EncryptionManager.decrypt(encryptedData, this.keyPath);
            File tmpFile = new File("addressbook.json");

            // Create a new file if it doesn't exist
            tmpFile.createNewFile();

            // Write the json string into the temp file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {
                writer.write(jsonData);
            }

            if (destinationFile == null) {
                // If no destination file is set, show file chooser asynchronously
                // This will be the main scenario except for testing.

                destinationFile = chooseExportLocation(new Stage());
                if (destinationFile == null) {
                    // User cancels the export
                    return new CommandResult(NO_DESTINATION_MESSAGE);
                }
            }

            CompletableFuture<String> result = performExport(tmpFile, destinationFile);
            String feedback = result.join();
            return new CommandResult(feedback);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Copies the address book data to the specified destination file.
     * @param sourceFile The source JSON file.
     * @param destinationFile The destination file chosen by the user.
     * @return A CommandResult indicating the success or failure of the export.
     */
    private CompletableFuture<String> performExport(File sourceFile, File destinationFile) {
        try {
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(sourceFile.toPath());
            return CompletableFuture.supplyAsync(() -> MESSAGE_SUCCESS
                    + " Data saved to: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            return CompletableFuture.supplyAsync(() -> "Error exporting data: " + e.getMessage());
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
