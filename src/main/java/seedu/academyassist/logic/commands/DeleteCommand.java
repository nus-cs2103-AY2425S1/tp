package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;

/**
 * Deletes a student identified using {@code StudentId} from the management system.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the student id.\n"
            + "Format: " + COMMAND_WORD + " STUDENT_ID\n"
            + "(" + StudentId.MESSAGE_CONSTRAINTS + ")\n"
            + "Parameter example:\n"
            + "- STUDENT_ID: S1234567A\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Student %1$s (%2$s) is successfully deleted.\n";

    private final StudentId targetStudentId;

    public DeleteCommand(StudentId targetStudentId) {
        this.targetStudentId = targetStudentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPersonWithStudentId(targetStudentId)) {
            throw new CommandException(Messages.MESSAGE_NO_STUDENT_FOUND);
        }

        Person personToDelete = model.getPersonWithStudentId(targetStudentId);
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName(),
                personToDelete.getStudentId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetStudentId.equals(otherDeleteCommand.targetStudentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", targetStudentId)
                .toString();
    }
}
