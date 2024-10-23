package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Model;

/**
 * Lists all archive files in the archive folder.
 */
public class ListArchiveFilesCommand extends Command {

    public static final String COMMAND_WORD = "listArchives";

    public static final String MESSAGE_SUCCESS = "Listed all archive files.";
    public static final String MESSAGE_FAILURE = "Failed to find archive files. Please try again later.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Path source = model.getAddressBookFilePath();
        assert source != null : "Address book file path is null";

        Path archiveDir = Paths.get(source.getParent().toString(), "archive");
        List<String> archiveFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(archiveDir, "*.json")) {
            for (Path entry : stream) {
                archiveFiles.add(entry.getFileName().toString());
            }
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        String resultMessage = String.join("\n", archiveFiles);
        return new CommandResult(MESSAGE_SUCCESS + "\n" + resultMessage);
    }
}
