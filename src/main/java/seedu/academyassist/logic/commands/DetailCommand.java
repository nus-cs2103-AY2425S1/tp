package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;

/**
 * Represents a command that is used to view the details of a particular student.
 */
public class DetailCommand extends Command {

    public static final String COMMAND_WORD = "detail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the details of a student. \n"
            + "Format: " + COMMAND_WORD + " STUDENT_ID\n"
            + "(" + StudentId.MESSAGE_CONSTRAINTS + ")\n"
            + "Parameter Example: \n"
            + "- STUDENT_ID: S00001\n";

    public static final String MESSAGE_DETAIL_SUCCESS = "Viewing details of student %1$s";

    private final StudentId targetStudentId;

    /**
     * @param targetStudentId of the student to be viewed
     */
    public DetailCommand(StudentId targetStudentId) {
        this.targetStudentId = targetStudentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPersonWithStudentId(targetStudentId) || targetStudentId == null) {
            throw new CommandException(Messages.MESSAGE_NO_STUDENT_FOUND);
        }

        Person personToView = model.getPersonWithStudentId(targetStudentId);

        return new CommandResult(String.format(MESSAGE_DETAIL_SUCCESS, personToView.getName(),
                personToView.getStudentId()), false, false, false, true, personToView);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handle nulls
        if (!(other instanceof DetailCommand)) {
            return false;
        }

        DetailCommand otherDetailCommand = (DetailCommand) other;
        return targetStudentId.equals(otherDetailCommand.targetStudentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", targetStudentId)
                .toString();
    }
}
