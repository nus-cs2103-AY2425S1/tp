package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * Deletes a student from the system based on the student number provided.
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "deletestu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the student name used in the displayed student list.\n"
            + "Parameters: STUDENT (must be a valid student name)\n"
            + "Example: " + COMMAND_WORD + " Jerrell Lee";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: ";

    public static final String MESSAGE_NONEXISTENT_STUDENT = "This student is not in your student list.";

    private final Name name;

    /**
     * Constructs a DeleteStudentCommand to delete the specified student by student number.
     *
     * @param name The student name of the student to be deleted.
     */
    public DeleteStudentCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    /**
     * Executes the command to delete a student from the system.
     *
     * @param model The model in which the student will be deleted.
     * @return A CommandResult indicating the success of the command.
     * @throws CommandException If the student number is invalid or the student does not exist in the list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Student studentToDelete = model.getStudentByName(name);

        if (studentToDelete == null) {
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }
        model.deleteStudent(studentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS) + studentToDelete.getName());
    }

    /**
     * Checks whether this DeleteStudentCommand is equal to another object.
     *
     * @param other The object to compare to.
     * @return True if the object is equal to this command, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteStudentCommand = (DeleteStudentCommand) other;
        return name.equals(otherDeleteStudentCommand.name);
    }
}
