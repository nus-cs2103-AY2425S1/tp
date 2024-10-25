package seedu.address.model;

import java.util.Stack;

import static seedu.address.logic.Messages.MESSAGE_REDO_FAILURE;
import static seedu.address.logic.Messages.MESSAGE_UNDO_FAILURE;

import seedu.address.model.exceptions.RedoException;
import seedu.address.model.exceptions.UndoException;

/**
 * Class to store past and undone CampusConnect data.
 */
public class VersionedCampusConnect {
    private final Stack<ReadOnlyCampusConnect> history = new Stack<>();
    private final Stack<ReadOnlyCampusConnect> future = new Stack<>();

    public ReadOnlyCampusConnect extractUndoneData() throws RedoException {
        if (future.isEmpty()) {
            throw new RedoException(MESSAGE_REDO_FAILURE);
        }
        return future.pop();
    }

    public ReadOnlyCampusConnect extractOldData() throws UndoException {
        if (history.isEmpty()) {
            throw new UndoException(MESSAGE_UNDO_FAILURE);
        }
        return history.pop();
    }

    public void saveOldData(ReadOnlyCampusConnect data) {
        history.add(data);
    }

    public void saveCurrentData(ReadOnlyCampusConnect data) {
        future.add(data);
    }

    public void clearUndoneData() {
        future.clear();
    }
}
