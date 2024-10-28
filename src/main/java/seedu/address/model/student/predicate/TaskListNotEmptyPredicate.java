package seedu.address.model.student.predicate;

import java.util.function.Predicate;

import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s {@code Level} matches any of the keywords given.
 */
public class TaskListNotEmptyPredicate implements Predicate<Student> {

    @Override
    public boolean test(Student student) {
        return student.getTaskList().size() > 0;
    }

}
