package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JFileChooser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicFilePath;

/**
 * Uploads a profile picture for a specified person
 */
public class UploadCommand extends Command {

    public static final String COMMAND_WORD = "upload";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads a profile picture from the computer"
            + "by the index number used in the last person listing. "
            + "Existing profile picture  will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Upload command not implemented yet";
    public static final String MESSAGE_UPLOAD_PERSON_SUCCESS = "Uploaded Profile Picture for Person: %1$s";
    public static final String MESSAGE_UPLOAD_CANCELLED = "Cancelled profile picture upload for Person: %1$s";
    public static final String MESSAGE_UPLOAD_ERROR = "Error getting profile picture. "
            + "Did you ensure it is a .png file?";
    private final Index targetIndex;

    /**
     * Default constructor for a UploadCommand object
     * @param targetIndex Index to be deleted
     */
    public UploadCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpload = lastShownList.get(targetIndex.getZeroBased());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                String newFileName = String.valueOf(personToUpload.hashCode()) + ".png";
                Path destinationPath = Paths.get("images", newFileName);

                Files.createDirectories(destinationPath.getParent());
                Files.copy(selectedFile.toPath(), destinationPath);

                ProfilePicFilePath newPath = new ProfilePicFilePath(destinationPath);
                Person editedPerson = new Person(
                        personToUpload.getName(), personToUpload.getPhone(), personToUpload.getEmail(),
                        personToUpload.getAddress(), personToUpload.getBirthday(), personToUpload.getTags(),
                        personToUpload.getHasPaid(), personToUpload.getFrequency(),
                        personToUpload.getProfilePicFilePath());

                model.setPerson(personToUpload, editedPerson);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

                return new CommandResult(String.format(MESSAGE_UPLOAD_PERSON_SUCCESS, personToUpload));
            } catch (IOException e) {
                throw new CommandException(MESSAGE_UPLOAD_ERROR);
            }
        } else {
            return new CommandResult(String.format(MESSAGE_UPLOAD_CANCELLED, personToUpload));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UploadCommand)) {
            return false;
        }

        UploadCommand otherUploadCommand = (UploadCommand) other;
        return targetIndex.equals(otherUploadCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
