package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

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

    public static final String MESSAGE_UPLOAD_PERSON_SUCCESS = "Uploaded Profile Picture for Person: %1$s";
    public static final String MESSAGE_UPLOAD_CANCELLED = "Cancelled profile picture upload for Person: %1$s";
    public static final String MESSAGE_UPLOAD_ERROR = "Error getting profile picture. "
            + "Did you ensure it is a .png file?";
    public static final String MESSAGE_UPLOAD_NOT_SUPPORTED = "Sorry! This feature is only currently "
            + "supported on Windows! Support for other OSes will come soon!";
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

        if (!System.getProperty("os.name").startsWith("Windows")) {
            throw new CommandException(MESSAGE_UPLOAD_NOT_SUPPORTED);
        }

        Person personToUpload = lastShownList.get(targetIndex.getZeroBased());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // adapted from https://stackoverflow.com/questions/19302029/filter-file-types-with-jfilechooser
        fileChooser.setFileFilter(new FileFilter() {

            public String getDescription() {
                return "PNG Images (*.png)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".png");
                }
            }
        });

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
                return new CommandResult(MESSAGE_UPLOAD_ERROR);
            }

            ProfilePicFilePath newPath = new ProfilePicFilePath(selectedFile.toPath());
            Person editedPerson = new Person(
                    personToUpload.getName(), personToUpload.getPhone(), personToUpload.getEmail(),
                    personToUpload.getAddress(), personToUpload.getBirthday(), personToUpload.getTags(),
                    personToUpload.getHasPaid(), personToUpload.getLastPaidDate(), personToUpload.getFrequency(),
                    newPath);

            model.setPerson(personToUpload, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_UPLOAD_PERSON_SUCCESS, personToUpload));

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
