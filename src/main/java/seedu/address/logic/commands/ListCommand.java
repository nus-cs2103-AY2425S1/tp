package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Lists all students in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";
    private Predicate<Student> previousPredicate;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        FilteredList<Student> filteredStudents = (FilteredList<Student>) model.getFilteredStudentList();
        @SuppressWarnings("unchecked")
        Predicate<Student> tmp = (Predicate<Student>) filteredStudents.getPredicate();
        previousPredicate = tmp;

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListCommand;
    }

    @Override
    public boolean undo(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(previousPredicate);
        return true;
    }
}
