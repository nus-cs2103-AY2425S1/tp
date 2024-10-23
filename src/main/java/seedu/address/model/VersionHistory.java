package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.exceptions.RedoLimitException;
import seedu.address.model.exceptions.UndoLimitException;

/**
 * Represents the history of addressbooks saved in memory.
 */
public class VersionHistory {
    private static final int MAXIMUM_INDEX = 99;
    private List<ReadOnlyAddressBook> versions = new ArrayList<>();
    private int currentVersionIndex = -1;

    /**
     * Initializes a new {@code VersionHistory} object with no previously saved history.
     */
    public VersionHistory() {
        versions = new ArrayList<>();
    }

    /**
     * Creates the system's {@code VersionHistory} object.
     * @param versionHistory   The {@code VersionHistory} object to be read.
     */
    public VersionHistory(VersionHistory versionHistory) {
        this.versions = versionHistory.versions;
        this.currentVersionIndex = versionHistory.currentVersionIndex;
    }

    public List<ReadOnlyAddressBook> getVersions() {
        return this.versions;
    }

    public int getCurrentVersionIndex() {
        return this.currentVersionIndex;
    }

    public void setCurrentVersionIndex(int versionIndex) {
        this.currentVersionIndex = versionIndex;
    }

    /**
     * Adds a version of the model to memory.
     * @param model  The current iteration to be saved.
     * @return       Returns an updated {@code VersionHistory} to be saved.
     */
    public VersionHistory addVersion(Model model) {
        ReadOnlyAddressBook dataToSave = new AddressBook().duplicateCopy(model.getAddressBook());
        if (currentVersionIndex == -1) {
            versions.add(dataToSave);
            currentVersionIndex++;
            return this;
        }

        if (currentVersionIndex == MAXIMUM_INDEX) {
            versions.remove(0);
            versions.add(dataToSave);
            return this;
        }

        if (currentVersionIndex < versions.size() - 1) {
            versions.subList(currentVersionIndex + 1, versions.size()).clear();
            versions.add(dataToSave);
            currentVersionIndex++;
            return this;
        }

        versions.add(dataToSave);
        currentVersionIndex++;
        return this;
    }

    /**
     * Undoes the current iteration of the T_Assistant.
     * @return   Returns a new {@code VersionHistory} with a new iteration of the T_Assistant saved to memory.
     * @throw    Throws an exception when the model cannot be undone any further.
     */
    public VersionHistory undoVersion() throws UndoLimitException {
        if (currentVersionIndex > 0) {
            currentVersionIndex--;
        } else {
            throw new UndoLimitException();
        }
        return this;
    }

    /**
     * Reverses the most recent undo command.
     * @return   Returns a new {@code VersionHistory} with a new iteration of the T_Assistant saved to memory.
     * @throw    Throws an exception when the model cannot be redone any further.
     */
    public VersionHistory redoVersion() throws RedoLimitException {
        if (currentVersionIndex < versions.size() - 1) {
            currentVersionIndex++;
        } else {
            throw new RedoLimitException();
        }
        return this;
    }
}
