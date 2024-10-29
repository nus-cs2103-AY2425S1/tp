package seedu.address.model.student.predicate;

import java.util.function.Predicate;

import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s {@code TaskList} is not empty.
 */
public class TaskListNotEmptyPredicate implements Predicate<Student> {

    @Override
    public boolean test(Student student) {
        return student.getTaskList().size() > 0;
    }

}
