package seedu.address.testutil;

import seedu.address.model.student.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Task MARKING_TASK = new TaskBuilder()
            .withTaskDescription("Mark homework")
            .withTaskDeadline("2024-01-01").build();

    public static final Task GRADING_TASK = new TaskBuilder()
            .withTaskDescription("Grade assignment")
            .withTaskDeadline("2024-12-31").build();
}
