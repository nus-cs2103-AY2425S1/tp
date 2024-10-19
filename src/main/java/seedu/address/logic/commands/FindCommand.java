package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Finds a person identified using their Student ID and displays their details.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds the student identified by the Student ID used and displays their details.\n"
            + "Parameters: "
            + "ID\n"
            + "Example: " + COMMAND_WORD + " "
            + "12345678";

    public static final String MESSAGE_FIND_PERSON_SUCCESS = "Found Student: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No student is found with Student ID: %1$s";
    private final StudentId studentId;

    /**
     * Creates a FindCommand to find the person identified by the specified {@code StudentId}.
     *
     * @param studentId The student ID of the person to find.
     * @throws NullPointerException if the {@code studentId} is null.
     */
    public FindCommand(StudentId studentId) {
        requireNonNull(studentId);
        this.studentId = studentId;
    }

    /**
     * Executes the find command and displays the student identified by the given studentID.
     *
     * @param model the model that contains the data of persons
     * @return a CommandResult that shows the outcome of the command
     * @throws CommandException if the studentID is invalid or not found
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person toFind = null;

        for (Person person : lastShownList) {
            if (person.getStudentId().equals(studentId)) {
                toFind = person;
                break;
            }
        }

        if (toFind == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentId));
        }

        model.setPersonToDisplay(toFind);
        return new CommandResult(String.format(MESSAGE_FIND_PERSON_SUCCESS, Messages.format(toFind)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return studentId.equals(otherFindCommand.studentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", studentId)
                .toString();
    }
}
