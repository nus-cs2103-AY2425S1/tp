package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.model.person.StudentId.isValidStudentId;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.exceptions.InvalidStudentIdException;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the Student ID used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_STUDENTID + "ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENTID + "12345678";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";

    private final StudentId studentId;

    public DeleteCommand(StudentId studentId) {
        requireNonNull(studentId);
        this.studentId = studentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete = null;

        for (Person person : lastShownList) {
            if (person.getStudentId().equals(studentId)) {
                personToDelete = person;
                break;
            }
        }

        if (personToDelete == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_STUDENT_ID, studentId));
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
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
        return studentId.equals(otherDeleteCommand.studentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .toString();
    }
}
