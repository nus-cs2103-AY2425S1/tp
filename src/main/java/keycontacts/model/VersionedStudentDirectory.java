package keycontacts.model;

import java.util.ArrayList;
import java.util.List;

import keycontacts.model.exceptions.StateNotFoundException;
import keycontacts.model.student.Student;

/**
 * Stores various states of the StudentDirectory that can be navigated between
 */
public class VersionedStudentDirectory extends StudentDirectory {

    private final List<List<Student>> studentDirectoryStateList;
    private int currentPointer = 0;

    /**
     * Creates a VersionedStudentDirectory using the Students in the {@code toBeCopied}
     */
    public VersionedStudentDirectory(ReadOnlyStudentDirectory toBeCopied) {
        super(toBeCopied);

        studentDirectoryStateList = new ArrayList<>();
        studentDirectoryStateList.add(new ArrayList<>(super.getStudentList()));
    }

    /**
     * Commits the current state of the {@code StudentDirectory} to the state list
     */
    public void commit() {
        for (int i = studentDirectoryStateList.size() - 1; i >= currentPointer + 1; i--) {
            studentDirectoryStateList.remove(i);
        }
        studentDirectoryStateList.add(new ArrayList<>(super.getStudentList()));

        currentPointer = currentPointer + 1;
        super.setStudents(studentDirectoryStateList.get(currentPointer));
    }

    /**
     * Moves to the previous state
     * @throws StateNotFoundException if it is already the earliest state
     */
    public void undo() throws StateNotFoundException {
        changeState(currentPointer - 1);
    }

    /**
     * Moves to the next state
     * @throws StateNotFoundException if it is already the latest state
     */
    public void redo() throws StateNotFoundException {
        changeState(currentPointer + 1);
    }

    private void changeState(int index) throws StateNotFoundException {
        if (index < 0 || index >= studentDirectoryStateList.size()) {
            throw new StateNotFoundException("Invalid state index");
        }

        currentPointer = index;
        super.setStudents(studentDirectoryStateList.get(index));
    }
}
