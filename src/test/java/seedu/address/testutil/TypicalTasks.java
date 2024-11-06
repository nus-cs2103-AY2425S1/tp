package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    // Strings for Task Descriptions
    public static final String VALID_TODO_DESCRIPTION = "Buy groceries";
    public static final String VALID_DEADLINE_DESCRIPTION = "Submit report";
    public static final String VALID_EVENT_DESCRIPTION = "Project meeting";
    public static final String VALID_SUBMIT_ASSIGNMENT_DESCRIPTION = "Submit assignment";

    // Dates for Deadline and Event tasks
    public static final String VALID_DEADLINE_DATE = "2024-12-31";
    public static final String VALID_EVENT_START_DATE = "2024-10-10";
    public static final String VALID_EVENT_END_DATE = "2024-10-11";

    public static final String TASK_DESC_TODO = " tk/Buy groceries";
    public static final String TASK_DESC_DEADLINE = " tk/Submit report d/2024-12-31";
    public static final String TASK_DESC_EVENT = " tk/Project meeting d/2024-10-10 d/2024-10-11";


    // Invalid Task Descriptions
    public static final String INVALID_TASK_DESC = " tk/ "; // incomplete event details

    // Sample Tasks
    public static final Todo TODO_TASK = new Todo(VALID_TODO_DESCRIPTION);
    public static final Deadline DEADLINE_TASK = new Deadline(VALID_DEADLINE_DESCRIPTION, VALID_DEADLINE_DATE);
    public static final Event EVENT_TASK = new Event(VALID_EVENT_DESCRIPTION,
            VALID_EVENT_START_DATE, VALID_EVENT_END_DATE);
    public static final Todo SUBMIT_ASSIGNMENT_TASK = new Todo(VALID_SUBMIT_ASSIGNMENT_DESCRIPTION); // New Task


    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    // Returns a list of all typical tasks
    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TODO_TASK, DEADLINE_TASK, EVENT_TASK));
    }
}
