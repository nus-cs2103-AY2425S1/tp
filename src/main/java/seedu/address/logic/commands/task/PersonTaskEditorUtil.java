package seedu.address.logic.commands.task;

import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

public class PersonTaskEditorUtil {

    /**
     * Creates and returns a {@code Person} with updated tasks.
     * This method can be used to either assign or unassign tasks to/from a person.
     *
     * @param personToEdit The person to edit.
     * @param updatedTasks The updated set of tasks (tasks added or removed).
     * @return A new Person object with the updated tasks.
     */
    public static Person createEditedPersonWithUpdatedTasks(Person personToEdit, Set<Task> updatedTasks) {
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getWeddings(),
                updatedTasks
        );
    }
}
