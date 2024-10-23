package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Task TASK_COMPLETED = new TaskBuilder()
        .withTaskName("Finish iP")
        .withDeadline(LocalDateTime.of(2024, 9, 15, 23, 59))
        .withStatus(Status.COMPLETED)
        .build();

    public static final Task TASK_PENDING = new TaskBuilder()
        .withTaskName("Finish tP v1.3")
        .withDeadline(LocalDateTime.of(2024, 9, 17, 23, 59))
        .withStatus(Status.PENDING)
        .build();

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.addTask(TASK_COMPLETED);
        ab.addTask(TASK_PENDING);
        return ab;
    }

}
