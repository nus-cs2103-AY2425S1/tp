package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;

/**
 * Adds an assignment to a tutorial group.
 */
public class AddAssignmentByTgCommand extends Command {

    public static final String COMMAND_WORD = "addatg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to a student. "
            + "Parameters: "
            + PREFIX_TUTORIAL_GROUP + "TUTORIAL GROUP "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_DEADLINE + "DEADLINE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_GROUP + "T15 "
            + PREFIX_ASSIGNMENT + "Math Quiz "
            + PREFIX_DEADLINE + "2024-10-09 ";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s to tutorial group %2$s";
    public static final String MESSAGE_NO_TUTORIAL_GROUP_FOUND = "No such tutorial group found!";

    public final Assignment assignment;
    public final TutorialGroup tutorialGroup;
    public final ArrayList<Student> students;

    /**
     * Creates an AddAssignmentByTgCommand to add the specified {@code Assignment}
     */
    public AddAssignmentByTgCommand(Assignment assignment, TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
        this.assignment = assignment;
        students = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> studentList = model.getStudentsByTutorialGroup(tutorialGroup);
        if (studentList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TUTORIAL_GROUP_FOUND);
        }

        for (Student student : studentList) {
            if (student.addAssignment(assignment)) {
                students.add(student);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, assignment.getAssignmentName(), tutorialGroup));
    }

    @Override
    public boolean undo(Model model) {
        for (Student student : students) {
            student.deleteAssignment(assignment.getAssignmentName());
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddAssignmentByTgCommand)) {
            return false;
        }

        AddAssignmentByTgCommand otherCommand = (AddAssignmentByTgCommand) other;
        return otherCommand.assignment.equals(this.assignment)
                && otherCommand.tutorialGroup.equals(this.tutorialGroup);
    }
}
