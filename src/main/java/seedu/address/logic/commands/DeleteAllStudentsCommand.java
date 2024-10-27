package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Deletes all students, clearing the list of students.
 */
public class DeleteAllStudentsCommand extends Command {
    public static final String COMMAND_WORD = "deleteall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all students from the student list.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted all students.";

    private ObservableList<Student> studentsToDelete;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        studentsToDelete = model.deleteAllStudents();
        return new CommandResult(MESSAGE_DELETE_STUDENT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DeleteAllStudentsCommand;
    }

    @Override
    public boolean undo(Model model) {
        model.replaceStudentList(studentsToDelete);
        return true;
    }
}
