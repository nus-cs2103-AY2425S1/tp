package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons, groups, and tasks lists.
     * These lists will not contain any duplicate persons.
     */
    ObservableList<Student> getStudentList();
    ObservableList<Group> getGroupList();
    ObservableList<Task> getTaskList();

}
