package keycontacts.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores various states of the StudentDirectory that can be navigated between
 */
public class VersionedStudentDirectory extends StudentDirectory {

    private final List<StudentDirectory> studentDirectoryStateList = new ArrayList<>();
    private int currentStatePointer = -1;

    /**
     * Creates a VersionedStudentDirectory using the Students in the {@code toBeCopied}
     */
    public VersionedStudentDirectory(ReadOnlyStudentDirectory toBeCopied) {
        super(toBeCopied);
        commit();
    }

    /**
     * Commits the current state of the {@code StudentDirectory} to the state list
     */
    public void commit() {
        for (int i = studentDirectoryStateList.size() - 1; i >= currentStatePointer + 1; i--) {
            studentDirectoryStateList.remove(i);
        }

        studentDirectoryStateList.add(new StudentDirectory(this));

        currentStatePointer = currentStatePointer + 1;
        this.resetData(studentDirectoryStateList.get(currentStatePointer));
    }

    /**
     * Moves to the previous state
     */
    public void undo() {
        changeState(currentStatePointer - 1);
    }

    /**
     * Moves to the next state
     */
    public void redo() {
        changeState(currentStatePointer + 1);
    }

    /**
     * Returns true if there is a successor state to redo to
     */
    public boolean canRedo() {
        return currentStatePointer != studentDirectoryStateList.size() - 1;
    }

    /**
     * Returns true if there is a predecessor state to undo to
     */
    public boolean canUndo() {
        return currentStatePointer != 0;
    }

    private void changeState(int index) {
        currentStatePointer = index;
        super.resetData(studentDirectoryStateList.get(index));
    }
}
