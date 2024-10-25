package seedu.address.model;

import static seedu.address.logic.Messages.MESSAGE_REDO_FAILURE;
import static seedu.address.logic.Messages.MESSAGE_UNDO_FAILURE;

import java.util.Stack;

import seedu.address.model.exceptions.RedoException;
import seedu.address.model.exceptions.UndoException;

/**
 * Class to store past and undone CampusConnect data.
 */
public class VersionedCampusConnect {
    private final Stack<ReadOnlyCampusConnect> history = new Stack<>();
    private final Stack<ReadOnlyCampusConnect> future = new Stack<>();

    /**
     * Extracts CampusConnect snapshots that have been undone.
     *
     * @return data in forms of ReadOnlyCampusConnect.
     * @throws RedoException when future stack is empty.
     */
    public ReadOnlyCampusConnect extractUndoneData() throws RedoException {
        if (future.isEmpty()) {
            throw new RedoException(MESSAGE_REDO_FAILURE);
        }
        return future.pop();
    }

    /**
     * Extracts history data.
     *
     * @return Old version of data in the form of ReadOnlyCampusConnect.
     * @throws UndoException when the storage is empty.
     */
    public ReadOnlyCampusConnect extractOldData() throws UndoException {
        if (history.isEmpty()) {
            throw new UndoException(MESSAGE_UNDO_FAILURE);
        }
        return history.pop();
    }

    /**
     * Saves history data into history stack.
     * Data should not be null.
     */
    public void saveOldData(ReadOnlyCampusConnect data) {
        assert data != null;
        history.add(data);
    }

    /**
     * Saves current data into future stack.
     * Data should not be null.
     */
    public void saveCurrentData(ReadOnlyCampusConnect data) {
        future.add(data);
    }

    /**
     * Clear all undone data in the future stack.
     */
    public void clearUndoneData() {
        future.clear();
    }
}
